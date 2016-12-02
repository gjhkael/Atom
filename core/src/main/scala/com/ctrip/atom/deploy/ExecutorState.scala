package com.ctrip.atom.deploy

/**
 * Created by huang_xw on 2016/2/24.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
object ExecutorState extends Enumeration {
    val LAUNCHING, LOADING, RUNNING, KILLED, FAILED, LOST, EXITED = Value
    type ExecutorState = Value

    def isFinished(state: ExecutorState): Boolean = Seq(KILLED, FAILED, LOST, EXITED).contains(state)
}
