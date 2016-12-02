package com.ctrip.atom.deploy.master

import com.ctrip.atom.deploy.Job.Application

/**
 * Created by huang_xw on 2016/2/4.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[master] object MasterMessages {

    case object CheckForWorkerTimeOut

    case object CheckAndLoadApps

    case class CleanApp(appId: String)

    case class RegisterApplication(app: Application)

    case class ModuleStatus(uuid: String)

    case class RegisterAppWithDescStr(desc: String)

    case object CheckAndScheduleQuartz

    case class CleanQuargzJob(uuid: String)

    case class KillRunningJob(scheduledId: String)


    case class RemoveWorker(worker: WorkerInfo, reason: String)

    case object CheckAndClear

}
