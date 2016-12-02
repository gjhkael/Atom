package com.ctrip.atom.deploy.executors

import com.ctrip.atom.Logging
import com.ctrip.atom.deploy.Job.SparkModule
import com.ctrip.atom.deploy.worker.CommandUtils

/**
 * Created by huang_xw on 2016/2/25.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 * Download and run the executor described in our SparkModule
 */
class SparkBuilder(sparkModule: SparkModule) extends Logging {
    def build(): ProcessBuilder = {
        val internalModule = sparkModule.internalModule
        val command = CommandUtils.buildSparkShellCommand(
            internalModule.libPathEntries,
            internalModule.mainJar,
            internalModule.mainClass,
            sparkModule.sparkArgs,
            sparkModule.paramsMap)
        logInfo("====================build command:" + command)
        CommandUtils.buildShellProcessBuilder(command, sparkModule.user)
    }
}
