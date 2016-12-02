package com.ctrip.atom.deploy.rest

import akka.actor.Actor
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._
import spray.http.HttpHeaders.RawHeader
import spray.routing.HttpService
import com.ctrip.atom.deploy.Job.Application

/**
 * Created by huang_xw on 2016/5/16.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class TestService extends Actor with HttpService {
    implicit val formats = DefaultFormats
    def rawJson = extract {
        _.request.entity.asString
    }

    val route = {
        respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
            pathPrefix("job") {
                (pathPrefix("register") & post) {
                    rawJson { json =>println(parse(json).extract[Application]) ;complete(json) }
                }
            }
        }
    }

    def actorRefFactory = context

    def receive = runRoute(route)
}
