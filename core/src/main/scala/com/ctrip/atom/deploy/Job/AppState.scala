package com.ctrip.atom.deploy.Job

/**
 * Created by huang_xw on 2016/2/3.
 * huang_xw@ctrip.com
 */
object AppState extends Enumeration {
    type AppState = Value
    val FAILED, WAITING, RUNNING, SUCCESS, SCHEDULING, SHUTDOWN,KILLED = Value
    def isFinishApp(state:AppState)=Seq(FAILED,SUCCESS,SHUTDOWN,KILLED).contains(state)
}
