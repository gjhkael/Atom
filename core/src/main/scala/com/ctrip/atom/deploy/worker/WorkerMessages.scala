package com.ctrip.atom.deploy.worker

/**
 * Created by huang_xw on 2016/4/24.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[worker] object WorkerMessages {

    case class RequestLog(uuid: String, lines: Int)

    case class LogResponse(logs: String)

    case object RequestStatus
    case object ClearModule

}
