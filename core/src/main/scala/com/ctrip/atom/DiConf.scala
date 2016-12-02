package com.ctrip.atom

import java.util.concurrent.ConcurrentHashMap

import com.ctrip.atom.util.Utils

import scala.collection.JavaConverters._

/**
 * Created by huang_xw on 2016/1/28.
 * huang_xw@ctrip.com
 */
class DiConf(loadDefaults: Boolean) extends Cloneable with Logging {
    def this() = {
        this(true)
    }

    private val settings = new ConcurrentHashMap[String, String]()
    for ((key, value) <- Utils.getSystemProperties if key.startsWith("atom")) {
        set(key, value)
    }

    /** Set a configuration variable. */
    def set(key: String, value: String): DiConf = {
        if (key == null) {
            throw new NullPointerException("null key")
        }
        if (value == null) {
            throw new NullPointerException("null value for " + key)
        }
        settings.put(key, value)
        this
    }

    def setIfMissing(key: String, value: String): DiConf = {
        if (!settings.contains(key)) set(key, value)
        this
    }

    def getAll: Array[(String, String)] = {
        settings.entrySet().asScala.map(x => (x.getKey, x.getValue)).toArray
    }

    /**
     * Return whether the given config is an akka config (e.g. akka.actor.provider).
     */
    def isAkkaConf(name: String): Boolean = name.startsWith("akka.")

    def getAkkaConf: Seq[(String, String)] = {
        getAll.filter { case (k, _) => isAkkaConf(k) }
    }

    /** Get a parameter,falling back to a default if not set */
    def get(key: String, defaultValue: String): String = {
        getOption(key).getOrElse {
            set(key, defaultValue)
            defaultValue
        }
    }

    def getLong(key: String, defaultValue: Long): Long = {
        getOption(key).map(_.toLong).getOrElse {
            set(key, defaultValue.toString);
            defaultValue
        }
    }

    /** Get a parameter as a boolean, falling back to a default if not set */
    def getBoolean(key: String, defaultValue: Boolean): Boolean = {
        getOption(key).map(_.toBoolean).getOrElse(defaultValue)
    }

    def getInt(key: String, defaultValue: Int): Int = {
        getOption(key).map(_.toInt).getOrElse {
            set(key, defaultValue.toString);
            defaultValue
        }
    }

    /** get a parameter as an Option */
    def getOption(key: String): Option[String] = {
        Option(settings.get(key))
    }

    def getTimeAsSeconds(key: String, defaultValue: String): Long = {
        //TODO
        0
    }
}

/**
 * Maps deprecated config keys to information about the deprecation.
 *
 * The extra information is logged as a warning when the config is present in
 * the user's configuration
 */
private[atom] object DiConf extends Logging {
    private val deprecatedConfigs: Map[String, DeprecatedConfig] = {
        val configs = Seq(DeprecatedConfig("di.cache.class", "0.1", "the di.ml.class property is no longer used!"))
        Map(configs.map { cfg => cfg.key -> cfg }: _*)
    }
}

private case class DeprecatedConfig(key: String, version: String, deprecationMessage: String)
