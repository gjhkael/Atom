package com.ctrip.atom.deploy.executors

import com.ctrip.atom.deploy.Job.ShellModule
import com.ctrip.atom.deploy.worker.CommandUtils
import com.ctrip.atom.{DiConf, Logging}

/**
 * Created by huang_xw on 2016/7/28.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class ShellBuilder(module: ShellModule) extends Logging {
    def build(): ProcessBuilder = {
        val command = module.mContent
        logInfo("====================build command:" + command)
        CommandUtils.buildShellProcessBuilder(command, module.user)
    }
}
