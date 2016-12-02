package com.ctrip.atom

import java.util.Locale

import scala.util.control.ControlThrowable

/**
 * Created by huang_xw on 2015/11/27.
 * huang_xw@ctrip.com
 * tel:15921782385
 */
object BaseUtil {

  def megabytesToString(megabytes: Long): String = {
    bytesToString(megabytes * 1024L * 1024L)
  }

  /**
   * Convert a quantity in bytes to a human-readable string such as "4.0 MB".
   */
  def bytesToString(size: Long): String = {
    val TB = 1L << 40
    val GB = 1L << 30
    val MB = 1L << 20
    val KB = 1L << 10

    val (value, unit) = {
      if (size >= 2 * TB) {
        (size.asInstanceOf[Double] / TB, "TB")
      } else if (size >= 2 * GB) {
        (size.asInstanceOf[Double] / GB, "GB")
      } else if (size >= 2 * MB) {
        (size.asInstanceOf[Double] / MB, "MB")
      } else if (size >= 2 * KB) {
        (size.asInstanceOf[Double] / KB, "KB")
      } else {
        (size.asInstanceOf[Double], "B")
      }
    }
    "%.1f %s".formatLocal(Locale.US, value, unit)
  }

  def tryOrExit(block: => Unit): Unit = {
    try {
      block
    } catch {
      case e: ControlThrowable => throw e
      case t: Throwable => System.exit(-1)
    }
  }
}
