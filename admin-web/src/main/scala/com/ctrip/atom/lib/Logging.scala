package com.ctrip.atom.lib

import org.apache.log4j.{LogManager, PropertyConfigurator}
import org.slf4j.impl.StaticLoggerBinder
import org.slf4j.{Logger, LoggerFactory}


/**
 * Created by huang_xw on 2015/12/2.
 * huang_xw@ctrip.com
 */
trait Logging {
    //make the log filed transient so that objects with logging can
    //be serialized and used on another machine
    @transient private var log_ : Logger = null

    protected def logName = {
        this.getClass.getName.stripSuffix("$")
    }

    protected def log: Logger = {
        if (log_ == null) {
            initializeIfNecessary()
            log_ = LoggerFactory.getLogger(logName)
        }
        log_
    }

    protected def logInfo(msg: => String): Unit = {
        if (log.isInfoEnabled) log.info(msg)
    }

    protected def logDebug(msg: => String): Unit = {
        if (log.isDebugEnabled) log.debug(msg)
    }

    protected def logTrace(msg: => String) {
        if (log.isTraceEnabled) log.trace(msg)
    }

    protected def logWaring(msg: => String) {
        if (log.isWarnEnabled) log.warn(msg)
    }

    protected def logError(msg: => String): Unit = {
        if (log.isErrorEnabled) log_.error(msg)
    }

    // Log methods that take Throwables (Exceptions/Errors) too
    protected def logInfo(msg: => String, throwable: Throwable) {
        if (log.isInfoEnabled) log.info(msg, throwable)
    }

    protected def logDebug(msg: => String, throwable: Throwable) {
        if (log.isDebugEnabled) log.debug(msg, throwable)
    }

    protected def logTrace(msg: => String, throwable: Throwable) {
        if (log.isTraceEnabled) log.trace(msg, throwable)
    }

    protected def logWarning(msg: => String, throwable: Throwable) {
        if (log.isWarnEnabled) log.warn(msg, throwable)
    }

    protected def logError(msg: => String, throwable: Throwable) {
        if (log.isErrorEnabled) log.error(msg, throwable)
    }

    protected def isTraceEnabled(): Boolean = {
        log.isTraceEnabled
    }

    private def initializeIfNecessary() {
        if (!Logging.initialized) {
            Logging.initLock.synchronized {
                if (!Logging.initialized) {
                    initializeLogging()
                }
            }
        }
    }

    private def initializeLogging() {
        // Don't use a logger in here, as this is itself occurring during initialization of a logger
        // If Log4j 1.2 is being used, but is not initialized, load a default properties file
        val binderClass = StaticLoggerBinder.getSingleton.getLoggerFactoryClassStr
        // This distinguishes the log4j 1.2 binding, currently
        // org.slf4j.impl.Log4jLoggerFactory, from the log4j 2.0 binding, currently
        // org.apache.logging.slf4j.Log4jLoggerFactory
        val usingLog4j12 = "org.slf4j.impl.Log4jLoggerFactory".equals(binderClass)
        if (usingLog4j12) {
            val log4j12Initialized = LogManager.getRootLogger.getAllAppenders.hasMoreElements
            if (!log4j12Initialized) {
                val defaultLogProps = "log4j-defaults.properties"
                Option(this.getClass.getClassLoader.getResource(defaultLogProps)) match {
                    case Some(url) =>
                        PropertyConfigurator.configure(url)
                        System.err.println(s"Using DI's default log4j profile: $defaultLogProps")
                    case None =>
                        System.err.println(s"DI was unable to load $defaultLogProps")
                }
            }
        }
        Logging.initialized = true
        // Force a call into slf4j to initialize it. Avoids this happening from multiple threads
        // and triggering this: http://mailman.qos.ch/pipermail/slf4j-dev/2010-April/002956.html
        log
    }
}

private object Logging {
    @volatile private var initialized = false
    val initLock = new Object()
    try {
        // We use reflection here to handle the case where users remove the
        // slf4j-to-jul bridge order to route their logs to JUL.
        val bridgeClass = Class.forName("org.slf4j.bridge.SLF4JBridgeHandler")
        bridgeClass.getMethod("removeHandlersForRootLogger").invoke(null)
        val installed = bridgeClass.getMethod("isInstalled").invoke(null).asInstanceOf[Boolean]
        if (!installed) {
            bridgeClass.getMethod("install").invoke(null)
        }
    } catch {
        case e: ClassNotFoundException => // can't log anything yet so just fail silently
    }
}
