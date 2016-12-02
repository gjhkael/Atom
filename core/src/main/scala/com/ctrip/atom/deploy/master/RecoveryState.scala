package com.ctrip.atom.deploy.master

/**
 * Created by huang_xw on 2016/2/5.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[deploy] object RecoveryState extends Enumeration {
    type RecoveryState = Value
    val STANDBY, ALIVE, RECOVERING, COMPLETING_RECOVERY = Value
}
