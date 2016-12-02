package com.ctrip.atom.util.logging

import java.io.{File, FileOutputStream, IOException, InputStream}

import com.ctrip.atom.util.{IntParam, Utils}
import com.ctrip.atom.{DiConf, Logging}

/**
 * Continuously appends the data from an input stream into the given file.
 */
private[atom] class FileAppender(inputStream: InputStream, file: File, bufferSize: Int = 8192)
  extends Logging {
    @volatile private var outputStream: FileOutputStream = null
    @volatile private var markedForStop = false
    // has the appender been asked to stopped
    @volatile private var stopped = false // has the appender stopped

    // Thread that reads the input stream and writes to file
    private val writingThread = new Thread("File appending thread for " + file) {
        setDaemon(true)

        override def run() {
            Utils.logUncaughtExceptions {
                appendStreamToFile()
            }
        }
    }
    writingThread.start()

    /**
     * Wait for the appender to stop appending, either because input stream is closed
     * or because of any error in appending
     */
    def awaitTermination() {
        synchronized {
            if (!stopped) {
                wait()
            }
        }
    }

    /** Stop the appender */
    def stop() {
        markedForStop = true
    }

    /** Continuously read chunks from the input stream and append to the file */
    protected def appendStreamToFile() {
        try {
            logDebug("Started appending thread")
            openFile()
            val buf = new Array[Byte](bufferSize)
            var n = 0
            while (!markedForStop && n != -1) {
                n = inputStream.read(buf)
                if (n != -1) {
                    appendToFile(buf, n)
                }
            }
        } catch {
            case e: Exception =>
                logError(s"Error writing stream to file $file", e)
        } finally {
            closeFile()
            synchronized {
                stopped = true
                notifyAll()
            }
        }
    }

    /** Append bytes to the file output stream */
    protected def appendToFile(bytes: Array[Byte], len: Int) {
        if (outputStream == null) {
            openFile()
        }
        try {
            outputStream.write(bytes, 0, len)
        } catch {
            case e: IOException =>
                logError(e.getMessage)
                logError(e.getStackTrace.mkString("\n\t"))
        }
    }

    /** Open the file output stream */
    protected def openFile() {
        outputStream = new FileOutputStream(file, false)
        logDebug(s"Opened file $file")
    }

    /** Close the file output stream */
    protected def closeFile() {
        outputStream.flush()
        outputStream.close()
        logDebug(s"Closed file $file")
    }
}

/**
 * Companion object to [[com.ctrip.atom.util.logging.FileAppender]] which has helper
 * functions to choose the correct type of FileAppender based on DiConf configuration.
 */
private[atom] object FileAppender extends Logging {

    /** Create the right appender based on Atom configuration */
    def apply(inputStream: InputStream, file: File, conf: DiConf): FileAppender = {

        import RollingFileAppender._

        val rollingStrategy = conf.get(STRATEGY_PROPERTY, STRATEGY_DEFAULT)
        val rollingSizeBytes = conf.get(SIZE_PROPERTY, STRATEGY_DEFAULT)
        val rollingInterval = conf.get(INTERVAL_PROPERTY, INTERVAL_DEFAULT)

        def createTimeBasedAppender(): FileAppender = {
            val validatedParams: Option[(Long, String)] = rollingInterval match {
                case "daily" =>
                    logInfo(s"Rolling executor logs enabled for $file with daily rolling")
                    Some(24 * 60 * 60 * 1000L, "--yyyy-MM-dd")
                case "hourly" =>
                    logInfo(s"Rolling executor logs enabled for $file with hourly rolling")
                    Some(60 * 60 * 1000L, "--yyyy-MM-dd--HH")
                case "minutely" =>
                    logInfo(s"Rolling executor logs enabled for $file with rolling every minute")
                    Some(60 * 1000L, "--yyyy-MM-dd--HH-mm")
                case IntParam(seconds) =>
                    logInfo(s"Rolling executor logs enabled for $file with rolling $seconds seconds")
                    Some(seconds * 1000L, "--yyyy-MM-dd--HH-mm-ss")
                case _ =>
                    logWaring(s"Illegal interval for rolling executor logs [$rollingInterval], " +
                      s"rolling logs not enabled")
                    None
            }
            validatedParams.map {
                case (interval, pattern) =>
                    new RollingFileAppender(
                        inputStream, file, new TimeBasedRollingPolicy(interval, pattern), conf)
            }.getOrElse {
                new FileAppender(inputStream, file)
            }
        }

        def createSizeBasedAppender(): FileAppender = {
            rollingSizeBytes match {
                case IntParam(bytes) =>
                    logInfo(s"Rolling executor logs enabled for $file with rolling every $bytes bytes")
                    new RollingFileAppender(inputStream, file, new SizeBasedRollingPolicy(bytes), conf)
                case _ =>
                    logWaring(s"Illegal size [$rollingSizeBytes] for rolling executor logs, rolling logs not enabled")
                    new FileAppender(inputStream, file)
            }
        }

        rollingStrategy match {
            case "" =>
                new FileAppender(inputStream, file)
            case "time" =>
                createTimeBasedAppender()
            case "size" =>
                createSizeBasedAppender()
            case _ =>
                logWaring(
                    s"Illegal strategy [$rollingStrategy] for rolling executor logs, " +
                      s"rolling logs not enabled")
                new FileAppender(inputStream, file)
        }
    }
}


