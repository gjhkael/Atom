package com.ctrip.atom.util

import java.io.{File, FileInputStream, IOException, InputStreamReader}
import java.net._
import java.util.{Properties, UUID}

import com.ctrip.atom.{DiConf, DiException, Logging}
import org.apache.commons.lang3.SystemUtils

//import org.eclipse.jetty.util.MultiException

import scala.collection.JavaConversions._
import scala.collection.Map
import scala.util.control.{ControlThrowable, NonFatal}

/**
 * Created by huang_xw on 2016/2/1.
 * huang_xw@ctrip.com
 */
object Utils extends Logging {
    def getDIClassLoader: ClassLoader = getClass.getClassLoader

    val isWindows = SystemUtils.IS_OS_WINDOWS
    val isMac = SystemUtils.IS_OS_MAC_OSX

    def createDirsIfNotExists(paths: List[String]): Boolean = paths.forall(createDirsIfNotExists)

    def createDirsIfNotExists(path: String): Boolean = {
        val dirs = new File(path)
        if (!dirs.exists()) {
            logDebug("create directory " + path);
            dirs.mkdirs()
        }
        else if (dirs.isDirectory) {
            logDebug(path + " exists")
            true
        }
        else {
            logError(s"create directory $path failed")
            false
        }
    }

    def generateUUID: String = {
        UUID.randomUUID().toString
    }

    def checkHost(host: String, message: String = "") = {
        assert(host.indexOf(':') == -1, message)
    }

    def tryOrExit(block: => Unit) {
        try {
            block
        } catch {
            case e: ControlThrowable => throw e
            case t: Throwable => t.printStackTrace(); System.exit(-1)
        }
    }

    /**
     * Execute a block of code that evaluates to Unit, re-throwing any non-fatal uncaught
     * exceptions as IOException.  This is used when implementing Externalizable and Serializable's
     * read and write methods, since Java's serializer will not report non-IOExceptions properly;
     */
    def tryOrIOException(block: => Unit) {
        try {
            block
        } catch {
            case e: IOException => throw e
            case NonFatal(t) => throw new IOException(t)
        }
    }

    def getSystemProperties: Map[String, String] = {
        val sysProps = for (key <- System.getProperties.stringPropertyNames()) yield
        (key, System.getProperty(key))
        sysProps.toMap
    }

    /**
     * Execute the given block, logging and re-throwing any uncaught exception.
     * This is particularly useful for wrapping code that runs in a thread, to ensure
     * that exceptions are printed, and to avoid having to catch Throwable.
     */
    def logUncaughtExceptions[T](f: => T): T = {
        try {
            f
        } catch {
            case ct: ControlThrowable =>
                throw ct
            case t: Throwable =>
                logError(s"Uncaught exception in thread ${Thread.currentThread().getName}", t)
                throw t
        }
    }

    @throws(classOf[IOException])
    def findRandomAvailablePortOnAllLocalInterfaces: Int = {
        val socket: ServerSocket = new ServerSocket(0)
        try {
            val port = socket.getLocalPort
            socket.close()
            port
        } finally {
            if (socket != null) socket.close()
        }
    }

    private def findLocalInetAddress(): InetAddress = {
        val defaultIpOverride = System.getenv("ATOM_LOCAL_IP")
        if (defaultIpOverride != null) {
            InetAddress.getByName(defaultIpOverride)
        } else {
            val address = InetAddress.getLocalHost
            if (address.isLoopbackAddress) {
                val activeNetworkIFs = NetworkInterface.getNetworkInterfaces.toList
                val reOrderedNetworkIFs = if (isWindows) activeNetworkIFs
                else activeNetworkIFs.reverse

                for (ni <- reOrderedNetworkIFs) {
                    val addresses = ni.getInetAddresses.toList.filterNot(addr => addr
                      .isLinkLocalAddress || addr.isLoopbackAddress)
                    if (addresses.nonEmpty) {
                        val addr = addresses.find(_.isInstanceOf[Inet4Address]).
                          getOrElse(addresses.head)
                        val strippedAddress = InetAddress.getByAddress(addr.getAddress)
                        logWaring("Your hostname, " + InetAddress.getLocalHost.getHostName + " " +
                          "resolves to" +
                          " a loopback address: " + address.getHostAddress + "; using " +
                          strippedAddress.getHostAddress + " instead (on interface " + ni.getName + ")")
                        logWaring("Set ATOM_LOCAL_IP if you need to bind to another address")
                        return strippedAddress
                    }
                }
                logWaring("Your hostname, " + InetAddress.getLocalHost.getHostName + " resolves to" +
                  " a loopback address: " + address.getHostAddress + ", but we couldn't find any" +
                  " external IP address!")
                logWaring("Set ATOM_LOCAL_IP if you need to bind to another address")
            }
            address
        }
    }

    private var customHostname: Option[String] = sys.env.get("ATOM_LOCAL_HOSTNAME")

    def setCustomHostname(hostname: String): Unit = {
        Utils.checkHost(hostname)
        customHostname = Some(hostname)
    }

    private lazy val localIpAddress: InetAddress = findLocalInetAddress()

    def localHostName(): String = {
        customHostname.getOrElse(localIpAddress.getHostAddress)
    }

    /** Return the absolute path of default atom properties file. */
    def getDefaultPropertiesFile(env: Map[String, String] = sys.env): String = {
        env.get("ATOM_CONF_DIR").orElse(env.get("ATOM_HOME").map { t =>
            new File(s"$t${File.separator}conf")
        }).map { t => new File(s"$t${
            File
              .separator
        }atom-defaults.conf")
        }.filter(_.isFile).map(_.getAbsolutePath).orNull
    }

    def getPropertiesFromFile(filename: String): Map[String, String] = {
        val file = new File(filename)
        require(file.exists(), s"Properties file $file does not exit")
        require(file.isFile, s"$file is not a normal file")
        val inReader = new InputStreamReader(new FileInputStream(file), "UTF-8")
        try {
            val properties = new Properties()
            properties.load(inReader)
            properties.stringPropertyNames().map { k => (k, properties(k).trim) }.toMap
        } catch {
            case e: IOException => throw new DiException(s"Failed when loading atom properties " +
              s"from $filename")
        } finally {
            inReader.close()
        }
    }

    /**
     * Convert a Java memory parameter passed to -Xmx (such as 300m or 1g) to a number of mebibytes.
     */
    def memoryStringToMb(str: String): Int = {
        (JavaUtils.byteStringAsBytes(str) / 1024 / 1024).toInt
    }

    def loadDefaultAtomProperties(conf: DiConf, filePath: String = null): String = {
        val path = Option(filePath).getOrElse(getDefaultPropertiesFile())
        Option(path).foreach { confFile =>
            getPropertiesFromFile(confFile).filter {
                case (k, v) => k.startsWith("atom.")
            }.foreach {
                case (k, v) => conf.setIfMissing(k, v)
                    sys.props.getOrElseUpdate(k, v)
            }
        }
        path
    }

    /**
     * Split the comma delimited string of master URLs into a list.
     * For instance, "atom://abc,def" becomes [atom://abc, atom://def].
     */
    def parseStandaloneMasterUrls(masterUrls: String): Array[String] = {
        masterUrls.stripPrefix("atom://").split(",").map("atom://" + _)
    }

    def getOrExit(str: String): String = {
        require(str != null && str.trim.length > 0, "empty string")
        str
    }

    def startServiceOnPort[T](
        startPort: Int,
        startService: Int => (T, Int),
        conf: DiConf,
        serviceName: String = ""): (T, Int) = {

        require(startPort == 0 || (1024 <= startPort && startPort < 65536),
            "startPort should be between 1024 and 65535 (inclusive), or 0 for a random free port.")

        val serviceString = if (serviceName.isEmpty) "" else s" '$serviceName'"
        val maxRetries = 3
        for (offset <- 0 to maxRetries) {
            // Do not increment port if startPort is 0, which is treated as a special port
            val tryPort = if (startPort == 0) {
                startPort
            } else {
                // If the new port wraps around, do not try a privilege port
                ((startPort + offset - 1024) % (65536 - 1024)) + 1024
            }
            try {
                val (service, port) = startService(tryPort)
                logInfo(s"Successfully started service$serviceString on port $port.")
                return (service, port)
            } catch {
                case e: Exception if isBindCollision(e) =>
                    if (offset >= maxRetries) {
                        val exceptionMessage =
                            s"${e.getMessage}: Service$serviceString failed after $maxRetries retries!"
                        val exception = new BindException(exceptionMessage)
                        // restore original stack trace
                        exception.setStackTrace(e.getStackTrace)
                        throw exception
                    }
                // logWarning(s"Service$serviceString could not bind on port $tryPort" +
                //s".Attempting port ${tryPort + 1}.")
            }
        }
        // Should never happen
        throw new DiException(s"Failed to start service$serviceString on port $startPort")
    }


    /**
     * Return a pair of host and port extracted from the `diurl`.
     *
     * A spark url (`atom://host:port`) is a special URI that its scheme is `atom` and only
     * contains
     * host and port.
     *
     */
    def extractHostPortFromAtomUrl(atomUrl: String): (String, Int) = {
        try {
            val uri = new java.net.URI(atomUrl)
            val host = uri.getHost
            val port = uri.getPort
            if (uri.getScheme != "atom" ||
              host == null ||
              port < 0 ||
              (uri.getPath != null && !uri.getPath.isEmpty) || // uri.getPath returns "" instead of null
              uri.getFragment != null ||
              uri.getQuery != null ||
              uri.getUserInfo != null) {
                throw new DiException("Invalid master URL: " + atomUrl)
            }
            (host, port)
        } catch {
            case e: java.net.URISyntaxException =>
                throw new DiException("Invalid master URL: " + atomUrl, e)
        }
    }

    /**
     * Return whether the exception is caused by an address-port collision when binding.
     */
    def isBindCollision(exception: Throwable): Boolean = {
        exception match {
            case e: BindException =>
                if (e.getMessage != null) {
                    return true
                }
                isBindCollision(e.getCause)
            //            case e: MultiException => e.getThrowables.exists(isBindCollision)
            case e: Exception => isBindCollision(e.getCause)
            case _ => false
        }
    }

    /**
     * Execute a block of code, then a finally block, but if exceptions happen in
     * the finally block, do not suppress the original exception.
     *
     * This is primarily an issue with `finally { out.close() }` blocks, where
     * close needs to be called to clean up `out`, but if an exception happened
     * in `out.write`, it's likely `out` may be corrupted and `out.close` will
     * fail as well. This would then suppress the original/likely more meaningful
     * exception from the original `out.write` call.
     */
    def tryWithSafeFinally[T](block: => T)(finallyBlock: => Unit): T = {
        // It would be nice to find a method on Try that did this
        var originalThrowable: Throwable = null
        try {
            block
        } catch {
            case t: Throwable =>
                // Purposefully not using NonFatal, because even fatal exceptions
                // we don't want to have our finallyBlock suppress
                originalThrowable = t
                throw originalThrowable
        } finally {
            try {
                finallyBlock
            } catch {
                case t: Throwable =>
                    if (originalThrowable != null) {
                        // We could do originalThrowable.addSuppressed(t), but it's
                        // not available in JDK 1.6.
                        logWarning(s"Suppressing exception in finally: " + t.getMessage, t)
                        throw originalThrowable
                    } else {
                        throw t
                    }
            }
        }
    }
}
