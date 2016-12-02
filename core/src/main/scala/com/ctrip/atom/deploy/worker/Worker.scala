package com.ctrip.atom.deploy.worker

import java.io.{File, IOException}
import java.text.SimpleDateFormat
import java.util.{Date, UUID}

import akka.actor._
import akka.remote.{DisassociatedEvent, RemotingLifecycleEvent}
import com.ctrip.atom.deploy.DeployMessage._
import com.ctrip.atom.deploy.Job.{Module, ModuleState}
import com.ctrip.atom.deploy.executors.ExecutorRunner
import com.ctrip.atom.deploy.master.Master
import com.ctrip.atom.deploy.master.ResponseMessages.GetModuleFieldResponse
import com.ctrip.atom.deploy.rest.RestMessage.GetFieldFromModule
import com.ctrip.atom.deploy.rest.RestServer
import com.ctrip.atom.deploy.worker.WorkerMessages.{ClearModule, LogResponse, RequestLog}
import com.ctrip.atom.deploy.{DeployMessage, ExecutorState}
import com.ctrip.atom.util.{ActorLogReceive, AkkaUtils, Utils}
import com.ctrip.atom.{BaseUtil, DiConf, Logging}
import org.apache.commons.io.FileUtils

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success, Try}

/**
 *
 * @param host host akka binding on
 * @param port worker port
 * @param cores  cores of worker
 * @param memory memory of worker (MB)
 * @param masterAkkaUrls master's url
 * @param actorSystemName actorSystemName
 * @param actorName actorName
 * @param workDirPath workDir
 * @param conf DiConf
 */
class Worker(
    host: String,
    port: Int,
    cores: Int,
    memory: Int,
    masterAkkaUrls: Array[String],
    actorSystemName: String,
    actorName: String,
    workDirPath: String,
    val conf: DiConf) extends ActorLogReceive with Actor with Logging {

    Utils.checkHost(host, "expected hostname!")
    assert(port > 0)

    @volatile private var registered = false
    @volatile private var connected = false

    private var master: ActorSelection = null
    private var activeMasterUrl: String = ""
    private var masterAddress: Address = null
    private val enableRestServer = true
    var memoryUsed = 0
    var coresUsed = 0
    val modules = new mutable.HashMap[String, Module]()
    val finishedModules = new mutable.HashMap[String, Module]()
    val executors = new mutable.HashMap[String, ExecutorRunner]

    def coresFree: Int = cores - coresUsed

    def memoryFree: Int = memory - memoryUsed

    private var connectionAttempCount: Int = 0
    //    private val HEARTBEAT_MILLIS = conf.getLong("atom.worker.timeout", 60) * 1000 / 4
    private val restServerEnabled = conf.getBoolean("atom.worker.rest.enabled", enableRestServer)
    private val INITIAL_REGISTRATION_RETRIES = 6
    private val TOTAL_REGISTRATION_RETRIES = INITIAL_REGISTRATION_RETRIES + 10
    private val FUZZ_MULTIPLIER_INTERVAL_LOWER_BOUND = 0.500
    private val REGISTRATION_RETRY_FUZZ_MULTIPLIER = {
        val randomNumberGenerator = new Random(UUID.randomUUID.getMostSignificantBits)
        randomNumberGenerator.nextDouble + FUZZ_MULTIPLIER_INTERVAL_LOWER_BOUND
    }
    private val INITIAL_MODULE_CLEAR_INTERVAL = conf.getInt("atom.worker.clear.interval", 10)
    private val INITIAL_REGISTRATION_RETRY_INTERVAL = math.round(10 *
      REGISTRATION_RETRY_FUZZ_MULTIPLIER).seconds
    private val PROLONGED_REGISTRATION_RETRY_INTERVAL = math.round(60
      * REGISTRATION_RETRY_FUZZ_MULTIPLIER).seconds
    private val workerId = generateWorkerId()
    private val testing: Boolean = sys.props.contains("atom.testing")
    private val atomHome =
        if (testing) {
            assert(sys.props.contains("atom.test.home"), "atom.test.home is not set!")
            new File(sys.props("atom.test.home"))
        } else {
            new File(sys.env.getOrElse("ATOM_HOME", "."))
        }
    conf.setIfMissing("atomHome", atomHome.getAbsolutePath)
    //for executor to get default classpath
    var workDir: File = null
    var registrationRetryTimer: Option[Cancellable] = None
    var heartBeatTimer: Option[Cancellable] = None

    //    private val workerUrl = "atom://" + host + ":" + port
    private def createDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")

    private val restServer =
        if (restServerEnabled) {
            val port = conf.getInt("atom.worker.rest.port", 8089)
            Some(new RestServer(host, port, conf, "WorkerLogService", self,
                classOf[WorkerLogService]))
        } else {
            None
        }
    private val restServerBoundPort = restServer.map(_.start())
    logInfo(s"Rest server bound port:$restServerBoundPort")

    private def generateWorkerId(): String = {
        "worker-%s-%s-%d".format(createDateFormat.format(new Date()), host, port)
    }

    private def createWorkDir(): Unit = {
        workDir = Option(workDirPath).map(new File(_)).getOrElse(new File(atomHome, "work"))
        try {
            workDir.mkdirs()
            if (!workDir.exists() || !workDir.isDirectory) {
                logError("Failed to create work directory " + workDir.getAbsolutePath)
                System.exit(1)
            }
            assert(workDir.isDirectory)
        } catch {
            case e: Exception =>
                logError("Failed to create work directory " + workDir, e)
                System.exit(1)
        }
    }

    private def tryRegisterAllMaters(): Unit = {
        for (masterAkkaUrl <- masterAkkaUrls) {
            logInfo("Connecting to Master " + masterAkkaUrl + "...")
            val actor = context.actorSelection(masterAkkaUrl)
            actor ! DeployMessage.RegisterWorker(workerId, host, port,
                cores, memory, workDir.getAbsolutePath, host)
        }
    }

    /**
     * Re-register with the master because a network failure or a master failure has occurred.
     * If the re-registration attempt threshold is exceeded, the worker exits with error.
     * Note that for thread-safety this should only be called from the actor.
     */
    private def reregisterWithMater(): Unit = {
        Utils.tryOrExit {
            connectionAttempCount += 1
            if (registered) {
                registrationRetryTimer.foreach(_.cancel())
                registrationRetryTimer = None
            } else if (connectionAttempCount <= TOTAL_REGISTRATION_RETRIES) {
                logInfo(s"Retrying connection to master (attemp # $connectionAttempCount)")
                if (master != null) {
                    master ! RegisterWorker(
                        workerId, host, port, cores, memory, workDir.getAbsolutePath, publicAddress = "")
                } else {
                    //We are retrying the initial registration
                    tryRegisterAllMaters()
                }
                //We have exceeded the initial registration retry threshold
                //All retries from now on should use a higher interval
                if (connectionAttempCount == INITIAL_REGISTRATION_RETRIES) {
                    registrationRetryTimer.foreach(_.cancel())
                    registrationRetryTimer = Some {
                        context.system.scheduler.schedule(PROLONGED_REGISTRATION_RETRY_INTERVAL,
                            PROLONGED_REGISTRATION_RETRY_INTERVAL, self, ReregisterWithMater)
                    }
                }
            } else {
                logError("All masters are unresponsive! Giving up.")
                System.exit(1)
            }
        }
    }

    private def registerWithMaster(): Unit = {
        registrationRetryTimer match {
            case None =>
                registered = false
                tryRegisterAllMaters()
                connectionAttempCount = 0
                registrationRetryTimer = Some {
                    context.system.scheduler.schedule(INITIAL_REGISTRATION_RETRY_INTERVAL,
                        INITIAL_REGISTRATION_RETRY_INTERVAL, self, ReregisterWithMater)
                }
            case _ => logInfo("Not spawning another attempt to register with the master, since there is an" +
              " attempt scheduled already.")
        }
    }

    private def changeMaster(url: String): Unit = {
        activeMasterUrl = url
        master = context.actorSelection(Master.toAkkaUrl(activeMasterUrl, AkkaUtils.protocol
          (context.system)))
        masterAddress = Master.toAkkaAddress(activeMasterUrl, AkkaUtils.protocol(context.system))
        connected = true
        registrationRetryTimer.foreach(_.cancel())
        registrationRetryTimer = None
    }

    private def masterDisconnected(): Unit = {
        logError("Connection to master failed! Waiting for master to reconnect...")
        connected = false
        registerWithMaster()
    }

    override def receiveWithLogging: PartialFunction[Any, Unit] = {
        case RegisteredWorker(masterUrl) =>
            logInfo("Successfully registered with master " + masterUrl)
            registered = true
            changeMaster(masterUrl)
            heartBeatTimer.foreach(_.cancel())
            heartBeatTimer = Option(context.system.scheduler.
              schedule(0.millis, 2000.millis, self, SendHeartBeat))

        case SendHeartBeat => if (connected) master ! Heartbeat(workerId)

        case RegisterWorkerFailed(message) =>
            if (!registered) {
                logError("Worker registration failed:" + message)
                System.exit(1)
            }

        case ReconnectWorker(masterUrl) =>
            logInfo(s"Master with url $masterUrl requested this worker to reconnect")
            registerWithMaster()

        case RequestWorkerState =>
            sender ! WorkerStateResponse(host, port, workerId, modules.values.toList,
                finishedModules.values.toList, activeMasterUrl, memory, memoryUsed)

        case x: DisassociatedEvent if x.remoteAddress == masterAddress =>
            logInfo(s"${x.remoteAddress} Disassociated!")
            masterDisconnected()

        case RequestLog(id, lines) =>
            modules.get(id) match {
                case None => sender ! LogResponse(s"module--$id is not exists!")
                case o: Option[Module] =>
                    sender ! LogResponse(o.get.getLogLines(lines))
            }

        case LaunchExecutor(user, masterUrl, moduleId, execId,
        module, cores_, memory_, executorDirPath) =>
            logInfo(s"======module type:${module.getClass.getName}======")
            if (masterUrl != activeMasterUrl) {
                logWaring(s"Invalid Master $masterUrl attempted to launch executor.")
            } else {
                try {
                    logInfo("Asked to launch executor %s for %s".format(execId, module))
                    val executorDir = new File(executorDirPath)
                    if (!executorDir.mkdirs()) {
                        throw new IOException("Failed to create directory " + executorDir)
                    }
                    modules.put(module.scheduledID, module)
                    val manager = new ExecutorRunner(moduleId, execId, module, cores_, memory_,
                        self, workerId, host, executorDir, conf, ExecutorState.LOADING)
                    executors(manager.fullId) = manager
                    manager.start()
                    module.startTail()
                } catch {
                    case e: Exception =>
                        logError(s"Failed to launch executor $moduleId/$execId for ${module.toString}")
                        if (executors.contains(moduleId + "/" + execId)) {
                            executors(moduleId + "/" + execId).kill()
                            executors -= moduleId + "/" + execId
                        }
                        self ! ExecutorStateChanged(moduleId, execId, ExecutorState.FAILED, Some(e
                          .toString), None)
                        e.printStackTrace()
                }
            }

        case KillExecutor(moduleId, execId) =>
            val fullId = moduleId + "/" + execId
            logInfo(s"kill executor:$fullId,current executors:")
            logInfo(executors.keySet.mkString("\n\t"))
            executors.get(fullId) match {
                case Some(executor) =>
                    logInfo(s"request to kill executor $fullId")
                    executor.kill()
                case None =>
                    logWaring(s"request to kill unknown executor $fullId")
            }
        case GetFieldFromModule(moduleId, field) =>
            logInfo(s"Current modules:${modules.mkString("\n\t")}")
            val response = Try(modules.get(moduleId).map(_.getField(field))) match {
                case Success(res) =>
                    if (res.isEmpty)
                        GetModuleFieldResponse("failed", "null")
                    else
                        GetModuleFieldResponse("success", res.get)
                case Failure(e) =>
                    GetModuleFieldResponse("failed", e.getMessage)
            }
            sender ! response
        case ClearModule => clearFinishModules()
        case ExecutorStateChanged(moduleId, execId, executorState, message, exitStatus) =>
            logInfo(s"Send executor{$execId} failed message to master.")
            master ! ExecutorStateChanged(moduleId, execId, executorState, message, exitStatus)
            val fullId = moduleId + "/" + execId
            logInfo(s"Executor state changed:$fullId")
            logInfo(s"Current executors:${executors.mkString("\n\t")}")
            if (ExecutorState.isFinished(executorState)) {
                executors.get(fullId) match {
                    case Some(executor) =>
                        logInfo("Executor " + fullId + " finished with state " + executorState +
                          message.map(" message " + _).getOrElse("") +
                          exitStatus.map(" exitStatus " + _).getOrElse(""))

                        executors -= fullId
                        coresUsed -= executor.cores
                        memoryUsed -= executor.memory
                        modules(moduleId).stopTail()
                        val normalExit = exitStatus == Option(0) && executorState !=
                          ExecutorState.KILLED
                        if (normalExit) {
                            modules(moduleId).setState(ModuleState.SUCCESS)
                        } else {
                            modules(moduleId).setState(ModuleState.FAILED)
                        }
                    case None =>
                        logInfo("Unknown Executor " + fullId + " finished with state " + executorState +
                          message.map(" message " + _).getOrElse("") +
                          exitStatus.map(" exitStatus " + _).getOrElse(""))
                }
            }

        case ReregisterWithMater => reregisterWithMater()
        case "heartBeat" => logInfo("heartbeat response form master:")
        //case _ => println(_)
    }

    override def preStart() {
        assert(!registered)
        logInfo("starting Atom worker %s:%d with %d cores,%s RAM".format(
            host, port, cores, BaseUtil.bytesToString(memory))
        )
        //        logInfo(s"Running Atom version $ATOM_VERSION")
        createWorkDir()
        context.system.eventStream.subscribe(self, classOf[RemotingLifecycleEvent])
        registerWithMaster()
        context.system.scheduler.schedule(Random.nextInt(10).seconds,
            INITIAL_MODULE_CLEAR_INTERVAL.seconds, self, WorkerMessages.ClearModule)
    }

    def clearFinishModules(): Unit = {
        modules.values.filter(m => ModuleState.isFinishModule(m.state) &&
          (System.currentTimeMillis() - m.getEndTime) / 1000 > INITIAL_MODULE_CLEAR_INTERVAL).map(
              m => modules -= m.scheduledID)
    }

    def launchSparkExecutor(): Unit = {

    }
}

object Worker {
    def main(argStrings: Array[String]) {
        val conf = new DiConf
        val args = new WorkerArguments(argStrings, conf)
        val (actorSystem, _) = startSystemAndActor(args.host, args.port, args.cores, args.memory,
            args.masters, args.workDir, conf)
        actorSystem.awaitTermination()
    }

    def startSystemAndActor(
        host: String,
        port: Int,
        cores: Int,
        memory: Int,
        masterUrls: Array[String],
        workDir: String,
        conf: DiConf): (ActorSystem, Int) = {
        val systemName = "atomWorker"
        val actorName = "Worker"
        val (actorSystem, boundPort) = AkkaUtils.createActorSystem(systemName, host, port, conf)
        val masterAkkaUrls = masterUrls.map(Master.toAkkaUrl(_, AkkaUtils.protocol(actorSystem)))
        actorSystem.actorOf(Props(classOf[Worker], host, boundPort, cores, memory, masterAkkaUrls,
            systemName, actorName, workDir, conf))
        (actorSystem, boundPort)
    }
}
