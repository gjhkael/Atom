package com.ctrip.atom.deploy.executors


import java.io.File

import akka.actor.ActorRef
import com.ctrip.atom.deploy.DeployMessage.ExecutorStateChanged
import com.ctrip.atom.deploy.ExecutorState
import com.ctrip.atom.deploy.Job.{Module, ShellModule, SparkModule, ZeppelinInterpreterModule}
import com.ctrip.atom.util.logging.FileAppender
import com.ctrip.atom.{DiConf, Logging}

/**
 * Created by huang_xw on 2016/2/25.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class ExecutorRunner(
    val moduleId: String,
    val execId: Int,
    val module: Module,
    val cores: Int,
    val memory: Int,
    val worker: ActorRef,
    val workerId: String,
    val host: String,
    val executorDir: File,
    conf: DiConf,
    @volatile var state: ExecutorState.Value) extends Logging {
    val fullId = moduleId + "/" + execId

    private var workerThread: Thread = null
    //    private val executorEnv: ExecutorEnv = _
    private var process: Process = null
    private var stdoutAppender: FileAppender = null
    private var stderrAppender: FileAppender = null

    private[deploy] def start() {
        workerThread = new Thread("ExecutorRunner for " + fullId) {
            override def run() {
                fetchAndRunExecutor()
            }
        }
        workerThread.start()
    }

    /** Stop this executor runner, including killing the process it launched */
    private[deploy] def kill() {
        if (workerThread != null) {
            // the workerThread will kill the child process when interrupted
            workerThread.interrupt()
            workerThread = null
            state = ExecutorState.KILLED
        }
    }

    /**
     * Kill executor process, wait for exit and notify worker to update resource status.
     * cause `destroy` can't kill process which has subprocess so we need to kill it with pid
     * @param message the exception message which caused the executor's death
     */
    private def killProcess(message: Option[String]) {
        var exitCode: Option[Int] = None
        if (process != null) {
            val f = process.getClass.getDeclaredField("pid")
            f.setAccessible(true)
            val pid = f.get(process).toString
            logInfo(s"Killing process!{$process} with pid $pid")
            if (stdoutAppender != null) {
                stdoutAppender.stop()
            }
            if (stderrAppender != null) {
                stderrAppender.stop()
            }
            exitCode = Some(Runtime.getRuntime.exec(s"sudo kill -TERM $pid").waitFor())
            //            process.destroy()
            //            exitCode = Some(process.waitFor())
        }
        logInfo("process kill succeed!")
        worker ! ExecutorStateChanged(moduleId, execId, state, message, exitCode)
    }

    /** Replace variables such as {{EXECUTOR_ID}} and {{CORES}} in a command argument passed to us */
    private[deploy] def substituteVariables(argument: String): String = argument match {
        case "{{EXECUTOR_ID}}" => execId.toString
        case "{{HOSTNAME}}" => host
        case "{{CORES}}" => cores.toString
        case "{{APP_ID}}" => moduleId
        case other => other
    }

    /**
     * Download and run the executor described in our SparkModule
     */
    private def fetchAndRunExecutor() {
        val builder: ProcessBuilder = module match {
            case module: SparkModule =>
                logInfo("=====spark model=====")
                new SparkBuilder(module.asInstanceOf[SparkModule]).build()
            case module: ZeppelinInterpreterModule =>
                logInfo("=====zeppelin model=====")
                new ZeppelinInterpreterBuilder(module.asInstanceOf[ZeppelinInterpreterModule]).build()
            case module: ShellModule =>
                logInfo("=====shell model=====")
                new ShellBuilder(module.asInstanceOf[ShellModule]).build()
        }
        try {
            process = builder.start()
            val stdout = new File(executorDir, "stdout")
            stdoutAppender = FileAppender(process.getInputStream, stdout,
                conf)
            val stderr = new File(executorDir, "stderr")
            stderrAppender = FileAppender(process.getErrorStream, stderr,
                conf)
            val exitCode = process.waitFor()
            state = ExecutorState.EXITED
            val message = "Command exited with code " + exitCode
            worker ! ExecutorStateChanged(moduleId, execId, state, Some(message), Some(
                exitCode))
            worker ! ExecutorStateChanged(moduleId, execId, state, Some(message), Some(exitCode))
        }
        catch {
            case interrepted: InterruptedException =>
                logInfo("Runner thread for executor " + fullId + " interrupted")
                state = ExecutorState.KILLED
                killProcess(None)
            case e: Exception =>
                logError("Error running executor", e)
                state = ExecutorState.FAILED
                killProcess(Some(e.toString))
        }
    }
}
