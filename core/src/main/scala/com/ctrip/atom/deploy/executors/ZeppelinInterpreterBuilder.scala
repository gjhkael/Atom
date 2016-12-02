package com.ctrip.atom.deploy.executors

import com.ctrip.atom.Logging
import com.ctrip.atom.deploy.Job.ZeppelinInterpreterModule
import com.ctrip.atom.deploy.worker.CommandUtils


/**
 * Created by huang_xw on 2016/8/8.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class ZeppelinInterpreterBuilder(module: ZeppelinInterpreterModule) extends Logging {
    def build(): ProcessBuilder = {
        //        val cmdLine = CommandLine.parse(module.interpreterRunner)
        //        cmdLine.addArgument("-d", false)
        //        cmdLine.addArgument(module.interpreterDir)
        //        cmdLine.addArgument("-p", false)
        //        cmdLine.addArgument(module.getPort.toString)
        //        cmdLine.addArgument("-l", false)
        //        cmdLine.addArgument(module.localRepoDir, false)
        //        val command = cmdLine.toString
        val command = s"${module.interpreterRunner} -d ${module.interpreterDir} -p ${
            module.
              getPort
        }  -l ${module.localRepoDir}"
        logInfo("====================build command:\n\t" + command)
        CommandUtils.buildShellProcessBuilder(command, module.user)
    }
}
