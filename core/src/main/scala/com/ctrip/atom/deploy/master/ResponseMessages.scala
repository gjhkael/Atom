package com.ctrip.atom.deploy.master

import org.json4s.DefaultFormats
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

/**
 * Created by huang_xw on 2016/5/16.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[atom] object ResponseMessages {

  case class RegisterAppResponse(status: String, reason: String) {
    implicit val formats = DefaultFormats

    override def toString: String = {
      compact(render(("status" -> status) ~ ("reason" -> reason)))
    }
  }

  case class ModuleStatusResponse(status: String, host: String) {
    override def toString: String = {
      compact(render(("status" -> status) ~ ("host" -> host)))
    }
  }

  case class GetRunningJobResponse(jobs: String) {
    override def toString: String = {
      compact(render("jobs" -> jobs))
    }
  }

  case class RequestRunningJobResponse(job: String, modules: Array[(String, String)]) {
    override def toString: String = {
      compact(render(("jobId" -> job) ~ ("modules" -> modules)))
    }
  }

  case class GetModuleFieldResponse(status: String, value: String) {
    override def toString: String = {
      compact(render(("status" -> status) ~ ("value" -> value)))
    }
  }

}
