package com.ctrip.atom.deploy.Job

import com.ctrip.atom.util.Utils

/**
 * Created by huang_xw on 2016/8/8.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class ZeppelinInterpreterModule(
    override val user: String,
    flowId: String,
    override val uuId: String,
    name: String,
    override val dependencies: List[String],
    override val appUUId: String,
    val interpreterRunner: String,
    val interpreterDir: String,
    val localRepoDir: String,
    override val mContent: String,
    //Driver's cores and memory to atom job
    override val memory: Int,
    override val cores: Int) extends ShellModule(user,
    flowId, uuId, name, dependencies, appUUId, mContent, memory, cores) {
    private var port: Int = 0

    def getPort: Int = {
        if (port == 0) {
            port = Utils.findRandomAvailablePortOnAllLocalInterfaces
        }
        port
    }
}
