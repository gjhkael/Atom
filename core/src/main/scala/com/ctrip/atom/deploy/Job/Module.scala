package com.ctrip.atom.deploy.Job

import java.io.File
import java.sql.Timestamp
import java.util.Date

import com.ctrip.atom.deploy.Job.ModuleState.ModuleState
import com.ctrip.atom.deploy.master.{ExecutorDesc, WorkerInfo}
import com.ctrip.atom.persistence.HibernateUtil
import com.ctrip.atom.persistence.entity.TModuleHistoryEntity
import com.ctrip.atom.util.LogTailerListener
import org.apache.commons.io.input.Tailer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by huang_xw on 2016/3/28.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
trait Module {
    val startTime = new Date()
    val dependencies: List[String]
    val uuId: String
    var state: ModuleState = _
    @transient var executor: ExecutorDesc = _
    val cores: Int
    val memory: Int
    val user: String
    val appUUId: String
    val TAIL_LOCK: String = "lock"
    var endTime: Long = Long.MaxValue
    @transient var nextExecutorId: Int = _
    @transient val moduleEntity = new TModuleHistoryEntity()
    var tailer: Tailer = _
    var logTailerListener: LogTailerListener = null
    val logBuffer: ArrayBuffer[String] = new ArrayBuffer[String] with mutable.SynchronizedBuffer[String]
    var logPath: String = null
    private var retries: Int = 0
    var quartzTimes = 0

    /**
     * When quartz job triggered then generate a new module by the original one for scheduling
     * @return
     */
    def generateQModule(): Module

    def init()

    def getEndTime: Long = {
        endTime
    }

    def getField(field: String): String = {
        val f = this.getClass.getDeclaredField(field)
        f.setAccessible(true)
        f.get(this).toString
    }

    def setState(state: ModuleState.Value): Unit = {
        this.synchronized {
            this.state = state
        }
        endTime = System.currentTimeMillis()
        if (moduleEntity != null) {
            moduleEntity.setStatus(state.toString)
            if (state == ModuleState.SUCCESS || state == ModuleState.FAILED) {
                moduleEntity.setEndTime(new Timestamp(endTime))
            }
            HibernateUtil.saveOrUpdate(moduleEntity)
        }
    }

    def getState(): Unit = {
        state
    }

    def isFinished: Boolean = {
        ModuleState.isFinishModule(state)
    }

    /**
     * ID for application which own this module
     * @return
     */
    def appScheduledID: String = {
        appUUId + "_" + quartzTimes
    }

    def scheduledID: String = {
        uuId + "_" + quartzTimes
    }

    private def newExecutorId(useID: Option[Int] = None): Int = {
        useID match {
            case Some(id) =>
                nextExecutorId = math.max(nextExecutorId, id + 1)
                id
            case None =>
                val id = nextExecutorId
                nextExecutorId += 1
                id
        }
    }

    def setExecutor(worker: WorkerInfo): ExecutorDesc = {
        val exec = new ExecutorDesc(user, newExecutorId(), this, worker, cores, memory)
        executor = exec
        logPath = exec.executorDirPath() + File.separator + "stderr"
        moduleEntity.setLogDir(logPath)
        moduleEntity.setWorker(worker.hostPort)
        HibernateUtil.saveOrUpdate(moduleEntity)
        exec
    }

    def scheduledDependencies: List[String] = {
        dependencies.map(_ + "_" + quartzTimes)
    }

    def removeExecutor(): Unit = {
        if (executor != null) {
            executor.worker.removeExecutor(executor)
        }
        executor = null
    }

    def getLogLines(line: Int): String = {
        logBuffer.takeRight(line).toList.distinct.mkString("\n")
    }

    /**
     * Tail current driver log
     */
    def startTail(): Unit = {
        new Thread(new Runnable {
            override def run(): Unit = {
                tailer = Tailer.create(new File(logPath), logTailerListener)
                tailer.run()
            }
        }).start()
    }

    def setQuartzTimes(times: Int): Unit = {
        this.quartzTimes = times
        moduleEntity.setScheduleTimes(quartzTimes)
    }

    def stopTail(): Unit = {
        Thread.currentThread()
        //for reading the last logs.
        Thread.sleep(1000)
        tailer.stop()
    }

    /**
     * Clear this module for retry
     */
    def retry(): Unit = {
        removeExecutor()
        retries += 1
        this.state = ModuleState.READY
    }

    def getProcessBuilder: ProcessBuilder
}
