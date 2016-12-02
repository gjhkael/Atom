package com.ctrip.atom.util

import java.io.{BufferedReader, File, InputStreamReader}

import ch.ethz.ssh2.{Connection, SCPClient, Session, StreamGobbler}
import com.ctrip.atom.Logging

/**
 * @param connInfo:ip,port,user,passwd/pemFile
 */
class ShellClient(val connInfo: (String, String, String, String)) extends Logging {
  var lines: collection.mutable.MutableList[String] = collection.mutable.MutableList()
  val conn = new Connection(connInfo._1, Integer.parseInt(connInfo._2))
  conn.connect()
  new File(connInfo._4).exists()
  if (!conn.authenticateWithPublicKey(connInfo._3, new File(connInfo._4), null)) throw new IllegalArgumentException("create connection failed")
  logInfo("connect successfully!")


  def executeScripts(script: String, fileName: String, tmpDir: String) = {
    executeSession(conn.openSession(), "if [ ! -d " + tmpDir + " ]; then \n mkdir -p " + tmpDir + "\n fi")
    executeSession(conn.openSession(), "if [  -f " + fileName + " ]; then \n rm -rf " + fileName + "\n fi")
    val scp = new SCPClient(conn)
    scp.put(script.getBytes, fileName, tmpDir, "0755")
    executeSession(conn.openSession, "bash " + tmpDir.trim() + "/" + fileName.trim())
  }

  def scpUpload(localPath: String, remotePath: String): Unit = {
    val scp = new SCPClient(conn)
    val preFile = new File(localPath)
    executeSession(conn.openSession(), "if [ ! -d " + remotePath + " ]; then \n mkdir -p " + remotePath + "\n fi \n")
    if (preFile.isDirectory)
      preFile.listFiles().foreach(file => {
        executeSession(conn.openSession(),
          "if [  -f " + remotePath + "/" + file + " ]; then \n rm -rf " + file + "\n fi")
        scp.put(file.getAbsolutePath, remotePath, "0600")
      })
    else
      scp.put(localPath, remotePath, "0600")
  }

  def executeCommand(command: String): Unit = {
    executeSession(conn.openSession(), command)
  }


  private def executeSession(session: Session, command: String) = {
    session.execCommand(command)
    val stdIs = new StreamGobbler(session.getStdout)
    val errIs = new StreamGobbler(session.getStderr)
    val stdBr = new BufferedReader(new InputStreamReader(stdIs))
    val errBr = new BufferedReader(new InputStreamReader(errIs))
    var line = stdBr.readLine()
    var errLine = errBr.readLine()
    while (line != null || errLine != null) {
      if (line != null) {
        println(line)
        lines += line
        line = stdBr.readLine()
      } else {
        println(errLine)
        lines += errLine
        errLine = errBr.readLine()
      }
    }
    lines += "ExitCode: " + session.getExitStatus
    println("ExitCode: " + session.getExitStatus)
    stdBr.close()
    session.close()
  }
}
