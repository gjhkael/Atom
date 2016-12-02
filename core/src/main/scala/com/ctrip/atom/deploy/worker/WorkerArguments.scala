package com.ctrip.atom.deploy.worker

import java.lang.management.ManagementFactory

import com.ctrip.atom.DiConf
import com.ctrip.atom.util.{IntParam, Utils}

/**
 * Created by huang_xw on 2016/2/15.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[worker] class WorkerArguments(args: Array[String], conf: DiConf) {
    var host = Utils.localHostName()
    var port = 0
    var cores = inferDefaultCores()
    var memory = inferDefaultMemory()
    var masters: Array[String] = null
    var workDir: String = null
    var propertiesFile: String = null

    if (System.getenv("ATOM_WORKER_PORT") != null) {
        port = System.getenv("ATOM_WORKER_PORT").toInt
    }

    if (System.getenv("ATOM_WORKER_CORES") != null) {
        cores = System.getenv("ATOM_WORKER_CORES").toInt
    }

    if (System.getenv("ATOM_WORKER_DIR") != null) {
        workDir = System.getenv("ATOM_WORKER_DIR")
    }

    parse(args.toList)

    propertiesFile = Utils.loadDefaultAtomProperties(conf, propertiesFile)

    def inferDefaultCores(): Int = {
        Runtime.getRuntime.availableProcessors()
    }

    private def parse(args: List[String]): Unit = args match {
        case ("--ip" | "-i") :: value :: tail =>
            Utils.checkHost(value, "ip no longer supported, please use hostname " + value)
            host = value
            parse(tail)

        case ("--host" | "-h") :: value :: tail =>
            Utils.checkHost(value, "Please use hostname " + value)
            host = value
            parse(tail)

        case ("--port" | "-p") :: IntParam(value) :: tail =>
            port = value
            parse(tail)

        case ("--cores" | "-c") :: IntParam(value) :: tail =>
            cores = value
            parse(tail)

        case ("--workDir" | "-d") :: value :: tail =>
            workDir = value
            parse(tail)

        case ("--properties-file") :: value :: tail =>
            propertiesFile = value
            parse(tail)

        case ("--help") :: tail =>
            printUsageAndExit(0)

        case value :: tail =>
            if (masters != null) {
                // Two positional arguments were given
                printUsageAndExit(1)
            }
            masters = Utils.parseStandaloneMasterUrls(value)
            parse(tail)

        case Nil =>
            if (masters == null) {
                // No positional argument was given
                System.err.println("master url must be set!")
                printUsageAndExit(1)
            }

        case _ =>
            System.err.println(s"unrecognized parameter! ")
            printUsageAndExit(1)
    }

    /**
     * Print usage and exit JVM with the given exit code.
     */
    def printUsageAndExit(exitCode: Int) {
        System.err.println(
            "Usage: Worker [options] <master>\n" +
              "\n" +
              "Master must be a URL of the form atom://hostname:port\n" +
              "\n" +
              "Options:\n" +
              "  -c CORES, --cores CORES  Number of cores to use\n" +
              "  -m MEM, --memory MEM     Amount of memory to use (e.g. 1000M, 2G)\n" +
              "  -d DIR, --work-dir DIR   Directory to run apps in (default: ATOM_HOME/work)\n" +
              "  -i HOST, --ip IP         Hostname to listen on (deprecated, please use --host or -h)\n" +
              "  -h HOST, --host HOST     Hostname to listen on\n" +
              "  -p PORT, --port PORT     Port to listen on (default: random)\n" +
              "  --properties-file FILE   Path to a custom Atom properties file.\n" +
              "                           Default is conf/atom-defaults.conf.")
        System.exit(exitCode)
    }

    def inferDefaultMemory(): Int = {
        val ibmVendor = System.getProperty("java.vendor").contains("IBM")
        var totalMb = 0
        try {
            val bean = ManagementFactory.getOperatingSystemMXBean()
            if (ibmVendor) {
                val beanClass = Class.forName("com.ibm.lang.management.OperatingSystemMXBean")
                val method = beanClass.getDeclaredMethod("getTotalPhysicalMemory")
                totalMb = (method.invoke(bean).asInstanceOf[Long] / 1024 / 1024).toInt
            } else {
                val beanClass = Class.forName("com.sun.management.OperatingSystemMXBean")
                val method = beanClass.getDeclaredMethod("getTotalPhysicalMemorySize")
                totalMb = (method.invoke(bean).asInstanceOf[Long] / 1024 / 1024).toInt
            }
        } catch {
            case e: Exception => {
                totalMb = 2 * 1024
                System.out.println("Failed to get total physical memory. Using " + totalMb + " MB")
            }
        }
        // Leave out 1 GB for the operating system, but don't return a negative memory size
        math.max(totalMb - 1024, 512)
    }

    def checkWorkerMemory(): Unit = {
        if (memory <= 0) {
            val message = "Memory can't be 0, missing a M or G on the end of the memory specification?"
            throw new IllegalStateException(message)
        }
    }
}
