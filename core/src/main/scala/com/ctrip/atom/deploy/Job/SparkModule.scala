package com.ctrip.atom.deploy.Job

import java.sql.Timestamp

import com.ctrip.atom.Logging
import com.ctrip.atom.deploy.executors.SparkBuilder
import com.ctrip.atom.util.LogTailerListener

import scala.collection.mutable

/**
 * Created by huang_xw on 2016/2/1.
 * huang_xw@ctrip.com
 */
private[atom] case class SparkModule(
    override val user: String,
    flowId: String,
    libId: String,
    override val uuId: String,
    name: String,
    override val dependencies: List[String],
    override val appUUId: String,
    //Driver's cores and memory to atom job
    override val memory: Int,
    override val cores: Int,
    sparkArgs: Map[String, String],
    paramsMap: Map[String, String]) extends Serializable with Module with Logging {
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
        moduleEntity.setSparkArgs(sparkArgs.mkString(","))
        moduleEntity.setParams(paramsMap.toList.mkString(","))
        moduleEntity.setEndTime(null)
        moduleEntity.setLogDir(null)
    }

    def reConfigSparkArgs(): Unit = {
        if (!(sparkArgs.contains("num-executors") && sparkArgs.contains("executor-memory"))) {
        }
    }

    /**
     * Prepare resource for running this module,
     */
    def prepareSource(): Unit = {
    }

    override def generateQModule(): SparkModule = {
        quartzTimes += 1
        val qModule = new SparkModule(user, flowId, libId, uuId, name,
            dependencies, appUUId, memory, cores, sparkArgs, paramsMap)
        qModule.setQuartzTimes(quartzTimes)
        qModule
    }

    /**
     * For job which platform has implemented
     */
    def internalModule: InternalModule = {
        idToInternalModule.get(libId).get
    }

    override def toString: String = {
        "libId:%s,scheduledId:%s,name:%s,dependencies:%s,appUUId:%s".
          format(libId, uuId, name, dependencies, appUUId)
    }

    override def getProcessBuilder: ProcessBuilder = {
        new SparkBuilder(this).build()
    }

    val idToInternalModule = {
        new mutable.HashMap[String, InternalModule]() +=("1" ->
          new InternalModule("1", "metadata", "com.ctrip.atom.ml.toolimpl.Metadata", Seq("metadata"),
              "atom-mllib_2.10-0.10.jar"),
          "2" -> new InternalModule("2", "beforeDummy", "com.ctrip.atom.ml.toolimpl.BeforeDummy", Seq(""),
              "atom-mllib_2.10-0.10.jar"),
          "3" -> new InternalModule("3", "dummy", "com.ctrip.atom.ml.toolimpl.Dummy", Seq("dummy"),
              "atom-mllib_2.10-0.10.jar"),
          "4" -> new InternalModule("4", "eda", "com.ctrip.atom.ml.toolimpl.Eda", Seq(""),
              "atom-mllib_2.10-0.10.jar"),
          "5" -> new InternalModule("5", "regression", "com.ctrip.atom.ml.toolimpl.Regression",
              Seq(""), "atom-mllib_2.10-0.10.jar"),
          "6" -> new InternalModule("6", "liftChart", "com.ctrip.atom.ml.toolimpl.LiftChart",
              Seq(""), "atom-mllib_2.10-0.10.jar"))
    }
}
