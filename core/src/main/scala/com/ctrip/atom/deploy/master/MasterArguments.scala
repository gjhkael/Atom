package com.ctrip.atom.deploy.master

import com.ctrip.atom.DiConf
import com.ctrip.atom.util.{IntParam, Utils}

/**
 * Created by huang_xw on 2016/2/15.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class MasterArguments(args: Array[String], conf: DiConf) {
    var host = Utils.localHostName()
    var port = 8088
    var propertiesFile: String = null
    var restPort=8080

    if (System.getenv("ATOM_MASTER_HOST") != null) {
        host = System.getenv("ATOM_MASTER_HOST")
    }
    if (System.getenv("ATOM_MASTER_PORT") != null) {
        port = System.getenv("ATOM_MASTER_PORT").toInt
    }
    parse(args.toList)
    propertiesFile = Utils.loadDefaultAtomProperties(conf, propertiesFile)

    private def parse(args: List[String]): Unit = args match {
        case ("--ip" | "-i") :: value :: tail =>
            Utils.checkHost(value, "Please use hostname " + value)
            host = value
            parse(tail)
        case ("--port" | "-p") :: IntParam(value) :: tail =>
            port = value
            parse(tail)
        case ("--properties-file") :: value :: tail =>
            propertiesFile = value
            parse(tail)
        case Nil => {}
        case _ =>
    }

    private def printUsageAndExit(exitCode: Int): Unit = {
        System.err.println(
            "Usage: Master [options]\n" +
              "\n" +
              "Options:\n" +
              "  -i HOST, --ip HOST     Hostname to listen on (deprecated, please use --host or -h) \n" +
              "  -h HOST, --host HOST   Hostname to listen on\n" +
              "  -p PORT, --port PORT   Port to listen on (default: 7077)\n" +
              "  --properties-file FILE Path to a custom Atom properties file.\n" +
              "                         Default is conf/atom-defaults.conf.")
        System.exit(exitCode)
    }

}
