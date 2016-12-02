package com.ctrip.atom.deploy.master

/**
 * Created by huang_xw on 2016/2/3.
 * huang_xw
 */
private[master] object WorkerState extends Enumeration {
    type WorkerState = Value
    val ALIVE, DEAD, DECOMMISSIONED, UNKNOWN = Value
}
