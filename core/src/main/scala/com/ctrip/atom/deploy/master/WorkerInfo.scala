package com.ctrip.atom.deploy.master

import akka.actor.ActorRef
import com.ctrip.atom.Logging
import com.ctrip.atom.deploy.Job.SparkModule
import com.ctrip.atom.deploy.master.WorkerState.WorkerState
import com.ctrip.atom.util.Utils

import scala.collection.immutable.HashMap
import scala.collection.mutable

/**
 * Created by huang_xw on 2016/2/3.
 * huang_xw@ctrip.com
 */
private[deploy] class WorkerInfo(
    val id: String,
    val host: String,
    val port: Int,
    val cores: Int,
    val memory: Int,
    val workDir: String,
    val actor: ActorRef,
    val pubicAddress: String)
  extends Serializable with Logging {

    Utils.checkHost(host, "Expected hostname")
    assert(port > 0)

    @transient var executors: mutable.HashMap[String, ExecutorDesc] = _
    // executorId => info
    @transient var state: WorkerState = _
    @transient var coresUsed: Int = _
    @transient var memoryUsed: Int = _
    @transient var modules: HashMap[String, SparkModule] = _

    @transient var lastHeartBeat: Long = _

    init()

    def coresFree: Int = cores - coresUsed

    def memoryFree: Int = memory - memoryUsed

    private def init(): Unit = {
        executors = new mutable.HashMap
        modules = new HashMap[String, SparkModule]()
        state = WorkerState.ALIVE
        coresUsed = 0
        memoryUsed = 0
        lastHeartBeat = System.currentTimeMillis()
    }

    def hostPort: String = {
        assert(port > 0)
        host + ":" + port
    }


    def addExecutor(exec: ExecutorDesc) {
        executors(exec.fullId) = exec
        coresUsed += exec.cores
        memoryUsed += exec.memory
    }

    def removeExecutor(exec: ExecutorDesc) {
        if (executors.contains(exec.fullId)) {
            executors -= exec.fullId
            coresUsed -= exec.cores
            memoryUsed -= exec.memory
        }
    }

    def hasExecutor(module: SparkModule): Boolean = {
        executors.values.exists(_.module == module)
    }

    def hasModule(module: SparkModule): Boolean = {
        modules.contains(module.uuId)
    }

    def setState(state: WorkerState.Value): Unit = {
        this.state = state
    }

    def getResourceString: String = {
        s"{address:$hostPort,memory:$memoryFree,cores:$coresFree}"
    }

}
