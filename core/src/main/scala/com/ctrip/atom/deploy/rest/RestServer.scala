package com.ctrip.atom.deploy.rest

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.ctrip.atom.DiConf
import com.ctrip.atom.util.Utils
import spray.can.Http

import scala.concurrent.Future
import scala.concurrent.duration._

class RestServer(
    host: String,
    port: Int,
    conf: DiConf,
    name: String,
    manager: ActorRef,
    sourceClass: Class[_]) {
    def start(): Int = {
        val (server, boundport) = Utils.startServiceOnPort[Future[Any]](port, doStart, conf, name)
        boundport
    }

    private def doStart(startPort: Int): (Future[Any], Int) = {
        implicit val restSystem = ActorSystem(name)
        implicit val timeout = Timeout(conf.getInt("atom.rest.timeout", 20) seconds)
        val restService = restSystem.actorOf(Props(sourceClass,host,port,conf,manager), name)
        val bind = IO(Http) ? Http.Bind(restService, interface = host, port = startPort)
        (bind, port)
    }
}


