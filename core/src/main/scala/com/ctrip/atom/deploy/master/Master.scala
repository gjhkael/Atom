package com.ctrip.atom.deploy.master

import java.io.File

import akka.actor._
import akka.remote.{AssociatedEvent, DisassociatedEvent, RemotingLifecycleEvent}
import akka.util.Timeout
import com.ctrip.atom.deploy.DeployMessage._
import com.ctrip.atom.deploy.Job._
import com.ctrip.atom.deploy.TypeDefined.APP
import com.ctrip.atom.deploy.master.MasterMessages.{RegisterApplication, _}
import com.ctrip.atom.deploy.master.ResponseMessages.{RequestRunningJobResponse, GetRunningJobResponse, ModuleStatusResponse, RegisterAppResponse}
import com.ctrip.atom.deploy.rest.RestMessage.CommonStatus
import com.ctrip.atom.deploy.rest.RestServer
import com.ctrip.atom.deploy.{DeployMessage, ExecutorState}
import com.ctrip.atom.util.Utils.extractHostPortFromAtomUrl
import com.ctrip.atom.util.{ActorLogReceive, AkkaUtils}
import com.ctrip.atom.{DiConf, Logging, RegisterException}
import org.quartz._
import org.quartz.impl.StdSchedulerFactory

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Random, Success, Try}

/**
 * Created by huang_xw on 2015/12/1.
 * huang_xw@ctrip.com
 */
class Master(
              host: String,
              port: Int,
              val conf: DiConf) extends ActorLogReceive with Actor with Logging {
  //Registered application
  private val idToCronApp = new mutable.HashMap[String, APP]()
  //running application
  private val idToApp = new mutable.HashMap[String, APP]()
  //Running module
  private val idToModule = new mutable.HashMap[String, Module]()

  private val APP_DIR = conf.get("atom.app.dir", "apps")
  if (!new File(APP_DIR).exists()) {
    new File(APP_DIR).mkdirs()
  }

  private val appsNeedToScheduled = new mutable.SynchronizedQueue[APP]()
  private val workers = new mutable.HashSet[WorkerInfo]()
  private val idToWorker = new mutable.HashMap[String, WorkerInfo]()
  private val addressToWorker = new mutable.HashMap[Address, WorkerInfo]()
  private val finishedModules = new ListBuffer[String]()
  private val WORKER_TIMEOUT: Long = conf.getLong("atom.worker.timeout", 5) * 1000
  private val QUARTZ_CHECK_INTERVAL: Long = conf.getLong("atom.quartz.checkinterval", 5) * 1000
  private val restServerEnabled = conf.getBoolean("atom.master.rest.enabled", true)
  private val masterUrl = "atom://" + host + ":" + port
  private val restServer =
    if (restServerEnabled) {
      val port = conf.getInt("atom.master.rest.port", 9009)
      Some(new RestServer(host, port, conf, "masterRestService", self, classOf[RestService]))
    } else {
      None
    }
  private val restServerBoundPort = restServer.map(_.start())
  logInfo(s"Rest server bound port:$restServerBoundPort")
  private val state = RecoveryState.STANDBY

  override def preStart() = {
    logInfo(s"Starting Atom master at $host:$port")
    logInfo(s"Running Atom version ${com.ctrip.atom.ATOM_VERSION}")
    context.system.eventStream.subscribe(self, classOf[RemotingLifecycleEvent])
    context.system.scheduler.schedule(1 seconds, WORKER_TIMEOUT millis, self, CheckForWorkerTimeOut)
    context.system.scheduler.schedule(3 seconds, QUARTZ_CHECK_INTERVAL millis, self, CheckAndScheduleQuartz)
  }

  override def preRestart(reson: Throwable, message: Option[Any]): Unit = {
    super.preRestart(reson, message)
    logError("Master actor restarted due to exception", reson)
  }

  override def receiveWithLogging = {
    case RegisterWorker(id, host, port, cores, memory, workDir, publicAddress) =>
      logInfo(s"registering worker $id,$host:$port,with $cores cores,$memory memory")
      if (idToWorker.contains(id)) {
        sender ! RegisterWorkerFailed("Duplicated id")
      } else {
        val worker = new WorkerInfo(id, host, port, cores, memory, workDir, sender(),
          publicAddress)
        if (registerWorker(worker)) {
          sender ! DeployMessage.RegisteredWorker(masterUrl)
        } else {
        }
      }
      logDebug("===================schedule========================")
      schedule()
    case ModuleSuccess(moduleScheduleId) =>
      idToModule(moduleScheduleId).setState(ModuleState.SUCCESS)
      checkForCompleteModule(idToModule(moduleScheduleId))
      checkAndClearApps()
    case ModuleFailed(moduleId, reason, stat) =>
      logInfo(s"module {$moduleId} failed due to $reason")
      val module = idToModule.get(moduleId)
      module match {
        case Some(moduleInfo) =>
          moduleInfo.setState(stat)
          val app = idToApp.get(moduleInfo.appScheduledID)
          idToApp.foreach(c => logInfo(moduleInfo.appScheduledID + "----" + c._1))
          app.foreach(a => {
            logInfo(s"{${a.scheduledID}--{${a.state}" +
              s"--{${AppState.isFinishApp(a.state)}}}")
            if (!AppState.isFinishApp(a.state))
              a.setState(AppState.FAILED)
          })
          cleanApp(app)
          checkAndClearApps()
        case None =>
      }
    case Heartbeat(workerId) =>
      idToWorker.get(workerId) match {
        case Some(workerInfo) => workerInfo.lastHeartBeat = System.currentTimeMillis()
        case None =>
          logWaring(s"Got heartbeat from unregistered worker $workerId")
          sender() ! ReconnectWorker(masterUrl)
      }
      logDebug(s" HeartBeat from worker:$workerId")
    case RemoveWorker(worker, reason) =>
      logInfo(s"Removing worker:${worker.id},cause $reason")
      removeWorker(worker)
    case RestServerHeartBeat => //TODO
    //case CheckAndLoadApps => loadApps()
    //case CheckAndClear => checkAndClearApps()
    case CheckAndScheduleQuartz => scheduleQuartzApp()
    case ScheduleApp(uuid) => logInfo(s"schedule app:$uuid")
    case RequestCommonStatus =>
      val runningFlows = idToApp.size
      val runningModules = idToModule.count(!_._2.isFinished)
      sender() ! CommonStatus(runningModules, runningFlows, 0, 0, 0, 0, 0)
    case RequestRunningJob(scheduledId: String) =>
      val runningJobs = idToApp.filter(_._1 == scheduledId)
      if (runningJobs.isEmpty) {
        RequestRunningJobResponse(scheduledId, null)
      } else {
        RequestRunningJobResponse(scheduledId,
          runningJobs.get(scheduledId).get.modules.map(r => (r.scheduledID, r.executor.worker.host)).toArray)
      }
    case CleanQuargzJob(uuid) =>
      logInfo(s"Remove quartz job from scheduling list:$uuid")
      if (idToCronApp.contains(uuid)) {
        val app = idToCronApp(uuid)
        cleanQuartzJob(app)
        app.setState(AppState.SHUTDOWN)
      }
      else
        logWaring(s"Try to remove quartz job which doesn't existed:$uuid}")
    case KillRunningJob(scheduledId) =>
      val app = idToApp.get(scheduledId)
      if (app.isDefined) {
        cleanApp(app)
        app.get.setState(AppState.KILLED)
      } else {
        logError("Trying to kill application which doesn't exists.")
      }
    case RegisterApplication(app: APP) =>
      if (app != null) {
        val res = Try(registerApplication(app)) match {
          case Failure(e: RegisterException) =>
            logError(s"Failed to register application:${app.scheduledID}")
            logError(e.getMessage)
            logError(e.getStackTrace.mkString("\n\t"))
            RegisterAppResponse("Failed", e.getMessage)
          case Success(_) => RegisterAppResponse("Success", "")
        }
        if (sender != self) {
          sender ! res
        }
      }
    case CheckForWorkerTimeOut => timeOutDeadWorkers()
    case ModuleStatus(uuid) =>
      val module = idToModule.get(uuid)
      if (module.isEmpty || module.get.executor == null)
        sender ! ModuleStatusResponse("null", "null")
      else
        sender ! ModuleStatusResponse(module.get.state.toString, module.get.executor.
          worker.host)

    case GetRunningJob =>
      logInfo("message:GetRunningJob")
      logInfo("runningJob:" + idToApp.mkString(","))
      val res = idToApp.filter(_._2.state == AppState.RUNNING).keys.mkString(",")
      sender ! GetRunningJobResponse(res)

    case ExecutorStateChanged(moduleScheduleId, execId, stat, message, exitStatus) =>
      val execOption = idToModule.get(moduleScheduleId).flatMap(module => Some(module.executor))
      execOption match {
        case Some(exec) =>
          val moduleInfo = idToModule(moduleScheduleId)
          exec.state = stat
          if (ExecutorState.isFinished(stat)) {
            logInfo(s"Removing exector ${exec.fullId} for module  ${moduleInfo.toString} because it is $stat")
            moduleInfo.removeExecutor()
          }
          val normalExit = exitStatus == Option(0) && stat != ExecutorState.KILLED
          if (stat == ExecutorState.KILLED) {
            self ! ModuleFailed(moduleScheduleId, message, ModuleState.KILLED)
          }
          //TODO retry to schedule the failed module.
          else if (!normalExit) {
            self ! ModuleFailed(moduleScheduleId, message, ModuleState.FAILED)
          } else {
            self ! ModuleSuccess(moduleScheduleId)
          }
        case None =>
          logWaring(s"Got status update for unknown executor $moduleScheduleId/$execId")
      }
    case x: AssociatedEvent => logInfo(s"Associate with ${x.remoteAddress}")
    case x: DisassociatedEvent =>
      if (addressToWorker.contains(x.remoteAddress)) {
        logError(s"${x.remoteAddress} got disassociated,removing it.")
        addressToWorker.get(x.remoteAddress).foreach(removeWorker)
      }
    case DeadLetter(msg, sender, recipient) =>
    case _ => logInfo(s"unknown message")
  }

  private def registerWorker(worker: WorkerInfo): Boolean = {
    workers.filter {
      w => (w.host == worker.host && w.port == worker.port) && (w.state == WorkerState.DEAD)
    }.foreach(w => workers -= w)
    val workerAddress = worker.actor.path.address
    if (addressToWorker.contains(workerAddress)) {
      val oldWorker = addressToWorker(workerAddress)
      if (oldWorker.state == WorkerState.UNKNOWN) {
        removeWorker(oldWorker)
      } else {
        logInfo("Attemped to re_register worker at same address: " + workerAddress)
        return false
      }
    }
    workers += worker
    idToWorker += (worker.id -> worker)
    addressToWorker += (workerAddress -> worker)
    true
  }

  /**
   * Schedule the currently available resources among waiting modules.This method will be called
   * every time a new app joins or resource availability changes.
   */
  private def schedule(): Unit = {
    if (state == RecoveryState.ALIVE) {
      return
    }
    val shuffledWorkers = Random.shuffle(workers)
    logInfo(s"\n\tSystem resource {${shuffledWorkers.map(_.getResourceString).mkString("\n\t")}}")
    for (worker <- shuffledWorkers if worker.state == WorkerState.ALIVE) {
      for (module <- idToModule.values.filter(_.state == ModuleState.READY)) {
        if (worker.coresFree > module.cores && worker.memoryFree > module.memory) {
          launchExecutor(worker, module.setExecutor(worker))
          module.setState(ModuleState.RUNNING)
        }
      }
    }
  }

  /**
   * Schedule module to worker.
   */
  private def launchExecutor(worker: WorkerInfo, exec: ExecutorDesc): Unit = {
    logInfo("Launching executor " + exec.fullId + " on worker " + worker.id)
    worker.addExecutor(exec)
    worker.actor ! LaunchExecutor(exec.user, masterUrl, exec.module.scheduledID, exec.id, exec
      .module, exec.module.cores, exec.module.memory, exec.executorDirPath())
  }

  private def checkForCompleteModule(module: Module): Unit = {
    val appScheduleId = module.appScheduledID
    logInfo("module complete:%s-%s with time cost:%d minutes!".
      format(appScheduleId, module.scheduledID,
        (System.currentTimeMillis() - module.startTime.getTime) / 60000))
    finishedModules += module.scheduledID
    val waitingModules = idToModule.filter(_._2.state == ModuleState.WAITING)
    waitingModules.filter(_._2.scheduledDependencies.forall(finishedModules.contains(_))).
      foreach(_._2.setState(ModuleState.READY))
    checkCompleteApp(appScheduleId)
  }

  private def checkCompleteApp(appScheduleId: String): Unit = {
    logInfo(s"check app state:{$appScheduleId}")
    idToApp.get(appScheduleId).foreach(ap =>
      if (ap.modules.forall(_.state == ModuleState.SUCCESS)) {
        ap.setState(AppState.SUCCESS)
        logInfo(s"Application $appScheduleId finished with time cost:${
          (System.currentTimeMillis() -
            ap.submitTime.getTime) / 60000
        } minutes!")
      })
  }

  /**
   *
   * @param appOption
     * TODO clean worker with running module!!
   */
  private def cleanApp(appOption: Option[APP]): Unit = {
    if (appOption.isEmpty) {
      logError("Trying to remove application which doesn't exists.")
    }
    else {
      val app = appOption.get
      logInfo(s" Trying to shutdown application {${app.scheduledID}} cause one of it's " +
        s"module has failed!")
      app.modules.
        filter(m => idToModule.contains(m.scheduledID) && m.state == ModuleState.RUNNING).
        foreach(mod => {
        mod.setState(ModuleState.FAILED)
        mod.executor.worker.actor ! KillExecutor(mod.scheduledID, mod.executor.id)
      })
    }
  }

  /**
   * Remove finished modules and apps from system
   */
  private def checkAndClearApps(): Unit = {
    logDebug("Clear System!")
    idToApp.values.foreach(app => {
      if (app.modules.count(!_.isFinished) == 0) {
        app.modules.foreach(idToModule -= _.scheduledID)
        logInfo(s"Clear app:${app.scheduledID}")
        idToApp -= app.scheduledID
      }
    })
    schedule()
  }

  /**
   * Remove job from quartz scheduler
   * @param app Application
   */
  private def cleanQuartzJob(app: APP): Unit = {
    try {
      idToCronApp(app.uuId).shutdown(false)
      idToCronApp(app.uuId).clear()
    } catch {
      case e: Exception => logWaring(s"shutdown quartz job $app")
    }
    idToCronApp.remove(app.uuId)
  }

  /**
   * Find dead workers and remove them
   */
  private def timeOutDeadWorkers(): Unit = {
    val currentTime = System.currentTimeMillis()
    val toRemove = workers.filter(currentTime - _.lastHeartBeat > WORKER_TIMEOUT)
    for (worker <- toRemove) {
      if (!(worker.state == WorkerState.DEAD)) {
        logWaring("Removing %s because we got no heartbeat in %d seconds".format(worker
          .id, (currentTime - worker.lastHeartBeat) / 1000))
        self ! RemoveWorker(worker, "worker dead")
      }
    }
  }

  /**
   * @param worker Worker need to be removed
   */
  private def removeWorker(worker: WorkerInfo): Unit = {
    logInfo("Removing worker:" + worker.id)
    worker.setState(WorkerState.DEAD)
    idToWorker -= worker.id
    addressToWorker -= worker.actor.path.address
    completeRecoveryOnWorker(worker)
  }

  /**
   * ReSchedule modules on the worker which is failed.
   */
  private def completeRecoveryOnWorker(worker: WorkerInfo): Unit = {
    logInfo(s"Rescheduling modules on failed worker:${worker.actor.path.address}...")
    val modules = idToModule.values.find(m => m.executor.worker == worker &&
      m.state == ModuleState.RUNNING)
    modules.foreach { m =>
      logInfo(s"Rescheduling module:${m.toString}")
      m.retry()
    }
  }

  /**
   * Create quartz job for application.
   * NOTE: When job misfire ,the system will ignore it.
   * @param app application
   */
  def createScheduleJob(app: APP): Unit = {
    logInfo(s"Add application to timer list:$app")
    val schedulerFactory = new StdSchedulerFactory()
    val scheduler = schedulerFactory.getScheduler
    val jobDetail = JobBuilder.newJob(classOf[AppScheduleJob]).
      withIdentity(app.uuId, app.uuId).build()
    jobDetail.getJobDataMap.put("app", app)
    jobDetail.getJobDataMap.put("appsNeedToScheduled", appsNeedToScheduled)
    jobDetail.getJobDataMap.put("idToApp", idToApp)
    val cronTrigger = TriggerBuilder.newTrigger().
      withIdentity(app.uuId, app.uuId).
      withSchedule(CronScheduleBuilder.cronSchedule(app.cronExpr).
      withMisfireHandlingInstructionDoNothing()).build()
    scheduler.scheduleJob(jobDetail, cronTrigger)
    app.setScheduler(scheduler)
    idToCronApp += (app.uuId -> app)
    scheduler.start()
  }

  /**
   * Register quartz job
   */
  def scheduleQuartzApp(): Unit = {
    while (appsNeedToScheduled.nonEmpty) {
      logInfo(s"\n\tDispatch list is not empty,start scheduling")
      val app = appsNeedToScheduled.dequeue()
      self ! registerApplication(app)
    }
  }

  /**
   * Register application to system,there are two app types
   * 1、normal application which scheduled immediately
   * 2、quartz application which scheduled by quartz scheduler
   * @param app Application
   */
  def registerApplication(app: APP): Unit = {
    if (verifyApp(app)) {
      if (app.isScheduleJob) {
        createScheduleJob(app)
        app.setState(AppState.SCHEDULING)
      } else {
        idToApp += (app.scheduledID -> app)
        logInfo(s"register app:${app.scheduledID}")
        app.setState(AppState.WAITING)
        registerAppModules(app)
      }
    }
  }

  private def registerAppModules(app: APP): Unit = {
    logInfo("register app modules!!!")
    app.modules.foreach(registerModule)
    app.setState(AppState.RUNNING)
    schedule()
  }

  /**
   * @param module Default for spark
   * @tparam T For others module like "shell、hive.."
   */
  def registerModule[T <: Module](module: T): Unit = {
    if (module.dependencies.isEmpty || module.dependencies.head == "00000") {
      module.setState(ModuleState.READY)
    } else {
      module.setState(ModuleState.WAITING)
    }
    idToModule += (module.scheduledID -> module)
  }

  def falseOrElse(flag: Boolean, throwable: Throwable): Boolean = {
    if (flag)
      throw throwable
    flag
  }

  /**
   * @param app Flow
   * @return
   */
  private def verifyApp(app: APP): Boolean = {
    logDebug(s"Submitting app:${app.toString()}")
    !falseOrElse(idToCronApp.values.exists(e => e.uuId == app.uuId) &&
      app.isScheduleJob,
      new RegisterException(s"Application is already registered{${app.uuId}}")) &&
      !falseOrElse(idToApp.contains(app.scheduledID), new RegisterException(
        "Application is running!")) &&
      !app.modules.exists(m => falseOrElse(idToModule.contains(m.scheduledID) || (app.
        isScheduleJob && idToCronApp.values.exists(e => e.modules.map(_.uuId).contains(m.uuId))),
        new RegisterException(s"{$m} is running or duplicate uuid")))
  }
}

object Master extends Logging {
  val systemName = "atomMaster"
  private val actorName = "Master"

  /**
   * Returns an `akka.tcp://...` URL for the Master actor given a atomUrl `atom://host:port`.
   *
   */
  def toAkkaUrl(atomUrl: String, protocol: String): String = {
    val (host, port) = extractHostPortFromAtomUrl(atomUrl)
    AkkaUtils.address(protocol, systemName, host, port, actorName)
  }

  def toAkkaAddress(atomUrl: String, protocol: String): Address = {
    val (host, port) = extractHostPortFromAtomUrl(atomUrl)
    Address(protocol, systemName, host, port)
  }

  def startMaster(argStrings: Array[String]): Unit = {
    val conf = new DiConf
    val args = new MasterArguments(argStrings, conf)
    val (actorSystem, _) = startSystemAndActor(args.host, args.port, args.restPort, conf)
    actorSystem.awaitTermination()
  }

  def main(argStrings: Array[String]) {
    startMaster(argStrings)
  }

  /**
   * Main entrance of system
   */
  def startSystemAndActor(host: String, port: Int, restPort: Int, conf: DiConf) = {
    implicit val restSystem = ActorSystem("restService")
    val (actorSystem, boundPort) = AkkaUtils.createActorSystem("atomMaster", host, port, conf)
    actorSystem.actorOf(Props(classOf[Master], host, port, conf), name = "Master")
    implicit val timeout = Timeout(20 seconds)
    (actorSystem, boundPort)
  }
}
