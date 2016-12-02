package com.ctrip.atom.util

import akka.actor.Actor
import org.slf4j.Logger

/**
 * Created by huang_xw on 2016/2/4.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 *
 * A trait to enable logging all akka actor messages
 */
private[atom] trait ActorLogReceive {
    self: Actor =>

    override def receive: Actor.Receive = new Actor.Receive {
        private val _receiveWithLogging = receiveWithLogging

        override def isDefinedAt(o: Any): Boolean = {
            val handled = _receiveWithLogging.isDefinedAt(o)
            if (!handled) {
                log.debug(s"Received unexpected actor system event:$o")
            }
            handled
        }

        override def apply(o: Any): Unit = {
            if (log.isDebugEnabled) {
                log.debug(s"[actor] received message $o from ${sender()}")
            }
            val start = System.nanoTime()
            _receiveWithLogging.apply(o)
            val timeTaken = (System.nanoTime() - start).toDouble / 1000000
            if (log.isDebugEnabled) {
                log.debug(s"[actor] handled message ($timeTaken) $o from ${sender()}")
            }
        }

//        //for debug
//        override def apply(o: Any): Unit = {
//            if (log.isInfoEnabled) {
//                log.info(s"[actor] received message $o from ${sender()}")
//            }
//            val start = System.nanoTime()
//            _receiveWithLogging.apply(o)
//            val timeTaken = (System.nanoTime() - start).toDouble / 1000000
//            if (log.isInfoEnabled) {
//                log.info(s"[actor] handled message ($timeTaken) $o from ${sender()}")
//            }
//        }

    }

    def receiveWithLogging: Receive

    protected def log: Logger
}
