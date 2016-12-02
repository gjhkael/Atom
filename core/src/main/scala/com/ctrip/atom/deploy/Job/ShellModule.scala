package com.ctrip.atom.deploy.Job

import java.sql.Timestamp

import com.ctrip.atom.deploy.executors.{ShellBuilder, SparkBuilder}
import com.ctrip.atom.util.LogTailerListener

/**
 * Created by huang_xw on 2016/7/28.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class ShellModule(
    override val user: String,
    flowId: String,
    override val uuId: String,
    name: String,
    override val dependencies: List[String],
    override val appUUId: String,
    val mContent: String,
    //Driver's cores and memory to atom job
    override val memory: Int,
    override val cores: Int) extends Module with Serializable {

    init()

    /**
     * Initialize module entity
     */
    def init() {
        state = ModuleState.WAITING
        nextExecutorId = 0
        logTailerListener = new LogTailerListener(logBuffer, TAIL_LOCK)
        moduleEntity.setStartTime(new Timestamp(startTime.getTime))
        moduleEntity.setFlowUuid(appUUId)
        moduleEntity.setOwner(user)
        moduleEntity.setScheduleTimes(quartzTimes)
        moduleEntity.setUuid(uuId)
        moduleEntity.setStatus(state.toString)
        moduleEntity.setId(flowId)
        moduleEntity.setSparkArgs(mContent)
        moduleEntity.setParams(mContent)
        moduleEntity.setEndTime(null)
        moduleEntity.setLogDir(null)
    }
    override def getProcessBuilder: ProcessBuilder = {
        new ShellBuilder(this).build()
    }
    override def generateQModule(): ShellModule = {
        quartzTimes += 1
        val qModule = new ShellModule(user, flowId, uuId, name,
            dependencies, appUUId, mContent, memory, cores)
        qModule.setQuartzTimes(quartzTimes)
        qModule
    }
}
