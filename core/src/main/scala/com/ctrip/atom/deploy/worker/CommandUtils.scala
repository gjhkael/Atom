package com.ctrip.atom.deploy.worker

import java.io.{File, FileFilter}

import com.ctrip.atom.Logging

import scala.collection.mutable.ListBuffer

/**
 * Created by huang_xw on 2016/2/24.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[deploy]
object CommandUtils extends Logging {
    def buildSparkShellCommand(
        libPath: List[String],
        mainJar: String,
        mainClass: String,
        sparkArgMap: Map[String, String],
        paramsArgsMap: Map[String, String]): String = {
        var jar = mainJar
        var entrance = mainClass
        val sparkHome = sys.env.getOrElse("SPARK_HOME", "/opt/app/spark-1.4.1")
        val atomHome = sys.env.getOrElse("ATOM_HOME", "")
        val userLib = sys.env.getOrElse("USER_LIB_DIR", "lib")
        //find jars in classpaths
        val libPaths = libPath.toBuffer
        val jars = ListBuffer[String]()
        val defaultLibPath = new File(Seq(atomHome, userLib).mkString(File.separator))

        if (defaultLibPath.isDirectory)
            libPaths += defaultLibPath.getAbsolutePath
        for (path <- libPaths) {
            if (new File(path).isDirectory)
                jars ++= new File(path).listFiles(new FileFilter {
                    override def accept(pathname: File): Boolean = pathname.getName.
                      endsWith(".jar")
                }).map(_.getAbsolutePath).toList
        }
        if (paramsArgsMap.contains("jar") && paramsArgsMap.contains("mainClass")) {
            jar = paramsArgsMap.get("jar").get
            entrance = paramsArgsMap.get("mainClass").get
        }
        val mainJarsPath = jars.filter(_.contains(jar))
        require(mainJarsPath.nonEmpty, s"$jar doesn't exists!")
        val sparkArgs = sparkArgMap.toList.map(tup => "--" + tup._1 + " " + tup._2).mkString(" ") +
          s" --jars ${jars.mkString(",")} --class $entrance ${mainJarsPath.head}"
        val paramsArgs = paramsArgsMap.toList.map(tup => tup._1.trim + "=" + tup._2.trim).
          mkString(" ")
        Seq(sparkHome + File.separator + "bin/spark-submit", sparkArgs, paramsArgs).mkString(" ")
    }


    /**
     * Build a ProcessBuilder based on the given parameters.
     * The `env` argument is exposed for testing.
     */
    def buildProcessBuilder(
        commandSeq: Seq[String],
        env: Map[String, String] = sys.env): ProcessBuilder = {
        val builder = new ProcessBuilder(commandSeq: _*)
        val environment = builder.environment()
        for ((key, value) <- env) {
            environment.put(key, value)
        }
        builder
    }

    def buildShellProcessBuilder(command: String, user: String): ProcessBuilder = {
        val finalCommand = s"sudo -u $user " + command
        buildProcessBuilder(Seq("sh", "-c", finalCommand))
    }
}

object test {
    def main(args: Array[String]) {
        val a = CommandUtils.buildProcessBuilder(Seq("/home/admin/huangxw/zeppelin-0.6" +
          ".0/bin/interpreter" +
          ".sh"), Map[String, String](("-d", "/home/admin/huangxw/zeppelin-0.6.0/interpreter/spark"),
            ("-p", "65656")))
        a.toString
    }
}
