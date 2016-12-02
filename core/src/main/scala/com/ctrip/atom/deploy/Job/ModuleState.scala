package com.ctrip.atom.deploy.Job

/**
 * Created by huang_xw on 2016/2/3.
 * huang_xw@ctrip.com
 */
object ModuleState extends Enumeration with Serializable {
    type ModuleState = Value
    val WAITING, READY, RUNNING, SUCCESS, FAILED, KILLED = Value

    def isFinishModule(state: ModuleState) = Seq(SUCCESS, FAILED, KILLED).contains(state)
}
