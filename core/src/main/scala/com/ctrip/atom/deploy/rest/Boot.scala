package com.ctrip.atom.deploy.rest

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.ctrip.atom.deploy.master.RestService
import spray.can.Http

import scala.concurrent.duration._

object Boot extends App {
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")
  // create and start our service actor
  val service = system.actorOf(Props[TestService], "demo-service")
  implicit val timeout = Timeout(100.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
