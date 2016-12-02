package com.ctrip.atom.deploy.worker

import akka.actor.{Actor, ActorRef}
import com.ctrip.atom.DiConf
import com.ctrip.atom.deploy.master.ResponseMessages.GetModuleFieldResponse
import com.ctrip.atom.deploy.rest.RestMessage.GetFieldFromModule
import com.ctrip.atom.util.AkkaUtils
import spray.http.HttpHeaders.RawHeader
import spray.routing.HttpService

import scala.concurrent.duration._

/**
 * Created by huang_xw on 2016/4/24.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class WorkerLogService(
    host: String,
    port: Int,
    conf: DiConf,
    worker: ActorRef) extends Actor with HttpService {

    def getFieldFromObject(moduleId: String, field: String): GetModuleFieldResponse = {
        AkkaUtils.askWithReply[GetModuleFieldResponse](GetFieldFromModule(moduleId, field), worker, 20.seconds)
    }

    val route = {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
            pathPrefix("log" / Segment) { id =>
                get {
                    val response = AkkaUtils.askWithReply[WorkerMessages.LogResponse](
                        WorkerMessages.RequestLog(id, 200), worker, 20 seconds)
                    complete(response.logs)
                }
            } ~
              pathPrefix("module") {
                  pathPrefix(Segment) {
                      moduleId => pathPrefix("field")(
                          pathPrefix(Segment) {
                              field => pathEnd {
                                  complete(getFieldFromObject(moduleId, field).toString)
                              }
                          })
                  }
              }
        }
    }

    def actorRefFactory = context

    def receive = runRoute(route)
}
