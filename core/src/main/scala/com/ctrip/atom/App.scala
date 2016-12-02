package com.ctrip.atom

import com.ctrip.atom.persistence.HibernateUtil
import com.ctrip.atom.util.{JarUtils, ShellClient}

/**
 * Entrance of the application
 */
object App {
  val user = "admin"
  var keypath = "D:\\Users\\huang_xw\\.ssh\\prod\\id_rsa"
  val sysOS = System.getProperties.getProperty("os.name")
  val localHome = "/Users/jack/IdeaProjects/di-ml-tool/"
  var testFile = "F:\\IdeaProjects\\di-ml-tool\\core\\src\\main\\resources\\pricepredict.xml"
  println(sysOS)
  if (sysOS.toLowerCase.contains("mac")) {
    keypath = "/Users/jack/.ssh/id_rsa"
    testFile = "/Users/jack/IdeaProjects/di-ml-tool/core/src/main/resources/pricepredict.xml"
  }
  val shells = Array(("bridge", "10.8.123.77", "50070", "admin"))
  val shellClients = shells.map { args => (args._1, new ShellClient(args._2, args._3, args._4,
    keypath))
  }
  val tmpDir = s"/home/$user/huangxw/di-ml-tool"
  val home = "/opt/app/di-ml-tool"

  def buildScript(filePath: String) = {
    s"cd $filePath \n bin/di-tool.sh metadata"
  }

  def clearDB(): Unit = {
    HibernateUtil.executeUpdate("delete  from TFlowHistoryEntity where uuid <> null", null)
    HibernateUtil.executeUpdate("delete  from TModuleHistoryEntity", null)
  }

  def buildCoreAndUpload(
                          shells: Array[(String, ShellClient)],
                          isPackage: Boolean = true): Unit = {
    val localPath = {
      if (isPackage)
        JarUtils.buildJar(sysOS).get(0)
      else
        s"${localHome}/core/target/atom-core_2.10-0.10.jar"
    }
    shells.foreach {
      _._2.executeCommand(
        s"ssh 10.8.217.57  '$home/sbin/stop-all.sh' \n " +
          s"ssh 10.8.217.58  '$home/sbin/stop-all.sh' \n " +
          s"ssh 10.8.217.59  '$home/sbin/stop-all.sh' \n ")
    }
    //clearDB()
    shells.foreach(shell => {
      shell._2.executeCommand(
        s"ssh 10.8.217.57 'rm -rf $home/lib/atom-core_2.10-*' \n" +
          s"ssh 10.8.217.58 'rm -rf $home/lib/atom-core_2.10-*' \n" +
          s"ssh 10.8.217.59 'rm -rf $home/lib/atom-core_2.10-*' ")

      shell._2.scpUpload(localPath, s"$tmpDir")
      shell._2.executeCommand(
        s"scp $tmpDir/atom-core_2.10-0.10.jar  10.8.217.57:/opt/app/di-ml-tool/lib/ \n" +
          s"scp $tmpDir/atom-core_2.10-0.10.jar  10.8.217.58:/opt/app/di-ml-tool/lib/\n" +
          s"scp $tmpDir/atom-core_2.10-0.10.jar  10.8.217.59:/opt/app/di-ml-tool/lib/\n" +
          "sleep 1 \n" +
          s"ssh 10.8.217.57 '$home/sbin/start-master.sh' \n" +
          "sleep 1 \n" +
          s"ssh 10.8.217.58 '$home/sbin/start-slave.sh  10.8.217.57:8088' \n" +
          "sleep 1 \n" +
          s"ssh 10.8.217.59 '$home/sbin/start-slave.sh  10.8.217.57:8088'")
    })
  }

  def main(args: Array[String]): Unit = {
    println(System.getProperties.getProperty("os.name"))
    buildCoreAndUpload(shellClients,true)
  }
}
