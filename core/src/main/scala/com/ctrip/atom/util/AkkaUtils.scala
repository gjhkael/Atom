package com.ctrip.atom.util

import akka.actor.{ActorRef, ActorSystem, ExtendedActorSystem}
import akka.pattern.ask
import com.ctrip.atom.{DiConf, DiException, Logging}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions.mapAsJavaMap
import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration

/**
 * Created by huang_xw on 2016/1/28.
 * huang_xw@ctrip.com
 */
private[atom] object AkkaUtils extends Logging {
    def createActorSystem(
        name: String,
        host: String,
        port: Int,
        conf: DiConf): (ActorSystem, Int) = {
        val startService: Int => (ActorSystem, Int) = { actualPort => doCreateActorSystem(name, host,
            port, conf)
        }
        Utils.startServiceOnPort(port, startService, conf, name)
    }

    def protocol(actorSystem: ActorSystem): String = {
        val akkaConf = actorSystem.settings.config
        val sslProp = "akka.remote.netty.tcp.enable-ssl"
        protocol(akkaConf.hasPath(sslProp) && akkaConf.getBoolean(sslProp))
    }

    def protocol(ssl: Boolean = false): String = {
        if (ssl) {
            "akka.ssl.tcp"
        } else {
            "akka.tcp"
        }
    }

    def address(
        protocol: String,
        systemName: String,
        host: String,
        port: Any,
        actorName: String): String = {
        s"$protocol://$systemName@$host:$port/user/$actorName"
    }

    private def doCreateActorSystem(
        name: String,
        host: String,
        port: Int,
        conf: DiConf): (ActorSystem, Int) = {
        val akkaConf = ConfigFactory.parseMap(conf.getAkkaConf.toMap[String, String])
          .withFallback(ConfigFactory.parseString(
            s"""
               |akka.daemonic = on
               |akka.loggers = [""akka.event.slf4j.Slf4jLogger""]
               |akka.stdout-loglevel = "ERROR"
               |akka.jvm-exit-on-fatal-error = off
               |akka.remote.transport-failure-detector.heartbeat-interval = 3s
               |akka.remote.transport-failure-detector.acceptable-heartbeat-pause = 10s
               |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
               |akka.remote.netty.tcp.transport-class = "akka.remote.transport.netty.NettyTransport"
               |akka.remote.netty.tcp.hostname = "$host"
               |akka.remote.netty.tcp.port = $port
               |akka.log-dead-letters = off
               |akka.remote.netty.tcp.tcp-nodelay = on
      """.stripMargin))
        val actorSystem = ActorSystem(name, akkaConf)
        val provider = actorSystem.asInstanceOf[ExtendedActorSystem].provider
        val boundPort = provider.getDefaultAddress.port.get
        (actorSystem, boundPort)
    }

    /**
     * Send a message to the given actor and get its result within a default timeout, or
     * throw a SparkException if this fails.
     */
    def askWithReply[T](
        message: Any,
        actor: ActorRef,
        timeout: FiniteDuration): T = {
        askWithReply[T](message, actor, maxAttempts = 1, retryInterval = Int.MaxValue, timeout)
    }

    /**
     * Send a message to the given actor and get its result within a default timeout, or
     * throw a SparkException if this fails even after the specified number of retries.
     */
    def askWithReply[T](
        message: Any,
        actor: ActorRef,
        maxAttempts: Int,
        retryInterval: Long,
        timeout: FiniteDuration): T = {
        // TODO: Consider removing multiple attempts
        if (actor == null) {
            throw new DiException(s"Error sending message [message = $message]" +
              " as actor is null ")
        }
        var attempts = 0
        var lastException: Exception = null
        while (attempts < maxAttempts) {
            attempts += 1
            try {
                val future = actor.ask(message)(timeout)
                val result = Await.result(future, timeout)
                if (result == null) {
                    throw new DiException("Actor returned null")
                }
                return result.asInstanceOf[T]
            } catch {
                case ie: InterruptedException => throw ie
                case e: Exception =>
                    lastException = e
                    logWaring(s"Error sending message [message = $message] in $attempts attempts")
            }
            if (attempts < maxAttempts) {
                Thread.sleep(retryInterval)
            }
        }
        throw new DiException(
            s"Error sending message [message = $message]", lastException)
    }
}


