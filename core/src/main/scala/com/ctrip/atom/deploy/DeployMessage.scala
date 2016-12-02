package com.ctrip.atom.deploy

import com.ctrip.atom.deploy.ExecutorState.ExecutorState
import com.ctrip.atom.deploy.Job.ModuleState.ModuleState
import com.ctrip.atom.deploy.Job.{Application, Module}

/**
 * Created by huang_xw on 2016/1/9.
 * huang_xw@ctrip.com
 */
private sealed trait DeployMessage extends Serializable

private object DeployMessage {

    //Worker to Master
    case class RegisterWorker(
        id: String,
        host: String,
        port: Int,
        cores: Int,
        memory: Int,
        workDir: String,
        publicAddress: String)

    case class RegisteredWorker(masterUrl: String) extends DeployMessage

    case class RegisterWorkerFailed(message: String) extends DeployMessage

    case class RegisterApplication[T <: Module](appDescription: Application) extends DeployMessage

    case class RegisterModule(moduleInfo: Module) extends DeployMessage

    case class UnregisterModule(moduleId: String)

    case object ReregisterWithMater

    case class LaunchExecutor(
        user: String,
        masterUrl: String,
        moduleId: String,
        execId: Int,
        module: Module,
        cores: Int,
        memory: Int,
        executorDirPath: String) extends DeployMessage

    case class ExecutorStateChanged(
        moduleId: String,
        execId: Int,
        state: ExecutorState,
        message: Option[String],
        exitStatus: Option[Int])
      extends DeployMessage

    case class KillExecutor(moduleId: String, execId: Int)

    case class ModuleSuccess(moduleId: String)

    case class ModuleFailed(moduleId: String, reason: Option[String], stat: ModuleState)

    case class Heartbeat(workerId: String)

    case object RequestCommonStatus
    case object GetRunningJob
    case class RequestRunningJob(scheduledId:String)

    case class ReconnectWorker(masterUrl: String)

    case class ScheduleApp(uuid: String)

    case object RequestWorkerState

    case class WorkerStateResponse(
        host: String, port: Int, workerId: String,
        modules: List[Module], finishedModules: List[Module], masterUrl: String,
        memory: Int, memoryUsed: Int)

    case object SendHeartBeat

    case object RestServerHeartBeat

}
