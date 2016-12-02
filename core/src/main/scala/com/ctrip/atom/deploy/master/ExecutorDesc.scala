package com.ctrip.atom.deploy.master

import java.io.File

import com.ctrip.atom.deploy.ExecutorState
import com.ctrip.atom.deploy.Job.Module

/**
 * Created by huang_xw on 2016/2/24.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[deploy] class ExecutorDesc(
                                    val user: String,
                                    val id: Int,
                                    val module: Module,
                                    val worker: WorkerInfo,
                                    val cores: Int,
                                    val memory: Int) {
  var state = ExecutorState.LAUNCHING

  def executorDirPath(): String = {
    worker.workDir + File.separator + module.scheduledID + "_" + module.user + "_" + System.currentTimeMillis()
  }

  def fullId: String = module.uuId + "/" + id

  override def equals(other: Any): Boolean = {
    other match {
      case info: ExecutorDesc =>
        fullId == info.fullId &&
          worker.id == info.worker.id &&
          cores == info.cores &&
          memory == info.memory
      case _ => false
    }
  }

  override def toString: String = fullId

  override def hashCode: Int = toString.hashCode()
}
