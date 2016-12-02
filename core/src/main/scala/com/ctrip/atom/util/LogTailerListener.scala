package com.ctrip.atom.util

import org.apache.commons.io.input.TailerListenerAdapter

import scala.collection.mutable.ArrayBuffer

/**
 * Created by huang_xw on 2016/4/24.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class LogTailerListener(logBuffer: ArrayBuffer[String], syncLock: AnyRef)
  extends TailerListenerAdapter with Serializable {
    override def handle(line: String): Unit = {
        syncLock.synchronized {
            logBuffer += line
        }
    }
}
