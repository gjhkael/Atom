package com.ctrip.atom.deploy.master

import akka.actor.{Actor, ActorRef}
import com.ctrip.atom.deploy.DeployMessage.{RequestRunningJob, GetRunningJob, RestServerHeartBeat}
import com.ctrip.atom.deploy.Job.{Application, ShellModule, SparkModule, ZeppelinInterpreterModule}
import com.ctrip.atom.deploy.master.MasterMessages._
import com.ctrip.atom.deploy.master.ResponseMessages.{RequestRunningJobResponse, GetRunningJobResponse, ModuleStatusResponse, RegisterAppResponse}
import com.ctrip.atom.persistence.HibernateUtil
import com.ctrip.atom.util.AkkaUtils
import com.ctrip.atom.{DiConf, Logging}
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._
import spray.http.HttpHeaders.RawHeader
import spray.routing.HttpService

import scala.util.{Failure, Success, Try}


// if you don't supply your own Protocol (see below)

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class RestService(
                   host: String,
                   port: Int,
                   conf: DiConf,
                   master: ActorRef) extends Actor with HttpService with Logging {
  implicit val formats = DefaultFormats

  /**
   *
   * @return 所有已完成flow数 平均用时 所有已完成module数  module平均用时 正在running的modules
   */
  def jobStatus(): Array[Double] = {
    val totalFlows = HibernateUtil.executeQuery(
      "Select count(*),sum(endTime-startTime) from TFlowHistoryEntity where endTime <> null",
      null).head.asInstanceOf[Array[Object]].map(Option(_).getOrElse("-1").toString.toDouble)
    val totalModules = HibernateUtil.executeQuery(
      "Select count(*),sum(endTime-startTime),sum(case status when 'FAILED' then 1 else 0 end)" +
        " from TModuleHistoryEntity where endTime <> null",
      null).head.asInstanceOf[Array[Object]].map(Option(_).getOrElse("-1").toString.toDouble)
    val runningModules = HibernateUtil.executeQuery("select count(*) from TModuleHistoryEntity " +
      "where endTime is null", null).head.toString.toDouble
    Array(totalFlows.head, totalFlows.last / totalFlows.head, totalModules.head, totalModules(1) /
      totalModules.head, runningModules, totalModules.last)
  }

  def rawJson = extract {
    _.request.entity.asString
  }

  def internalModules(): String = {
    val a = HibernateUtil.executeQuery("select text as name,type,libPath,ownerId," +
      "params from TElementTplEntity", null).map(_.asInstanceOf[Array[Object]].mkString(",")).
      map(_.split(","))
    val b = Array[String]("name", "type", "libPath", "ownerId", "params")
    val tmpRes = a.map(b.zip(_).map(tup => "\"" + tup._1 + "\":\"" + tup._2 + "\""))
    val res = for (i <- tmpRes.indices) yield "\"data_" + i + "\":{" + tmpRes(i).mkString(",") +
      "}"
    "{" + res.mkString(",\n") + "}"
  }

  val route = {
    respondWithHeader(RawHeader("Access-Control-Allow-Origin", "*")) {
      (pathPrefix("internalModules") & get) {
        pathEnd {
          complete {
            internalModules()
          }
        }
      } ~
        (pathPrefix("status") & get) {
          pathEnd {
            complete {
              jobStatus().map(r => if (r < 0) "N/A" else r.toLong.toString).mkString(",")
            }
          } ~ pathPrefix(Segment) {
            moduleId => val res =
              AkkaUtils.askWithReply[ModuleStatusResponse](ModuleStatus(moduleId), master,
                20.seconds)
              complete {
                res.toString
              }
          }
        } ~
        pathPrefix("job") {
          pathPrefix("scheduledJob") {
            pathPrefix(Segment) {
              flowId =>
                pathPrefix("stop") {
                  pathEnd {
                    master ! CleanQuargzJob(flowId)
                    complete("ok")
                  }
                }
            }
          } ~
            pathPrefix("runningJob") {
              pathEnd {
                get {
                  complete {
                    logInfo("send message to master to get RUnningJob")
                    val res = AkkaUtils.askWithReply[GetRunningJobResponse](GetRunningJob, master, 20.seconds)
                    res.toString
                  }
                }
              } ~
                pathPrefix(Segment) {
                  scheduledId => delete {
                    complete {
                      master ! KillRunningJob(scheduledId)
                      "ok"
                    }
                  } ~ get {
                    complete {
                      val res = AkkaUtils.askWithReply[RequestRunningJobResponse](RequestRunningJob(scheduledId),master,20.seconds)
                      res.toString
                    }
                  }
                }
            } ~
            (pathPrefix("register") & post) {
              rawJson { json =>
                val res: RegisterAppResponse =
                  Try(parseApp(json)) match {
                    case Success(app) =>
                      AkkaUtils.askWithReply[RegisterAppResponse](
                        RegisterApplication(app), master, 20.seconds)
                    case Failure(e) => RegisterAppResponse("Failed", e.getMessage)
                  }
                complete {
                  res.toString
                }
              }
            }
        } ~
        pathPrefix("usermodules") {
          pathPrefix(Segment) {
            userId =>
              pathEnd {
                complete {
                  "123"
                }
              }
          }
        }
    }
  }

  def parseApp(json: String): Application = {
    val t = parse(json)
    val vMap = t.removeField(_._1 == "modules").values.asInstanceOf[Map[String, String]]
    val moduleValues = t \ "modules"
    val modules = {
      vMap("type") match {
        case "spark" => moduleValues.extract[List[SparkModule]]
        case "shell" => moduleValues.extract[List[ShellModule]]
        case "zeppelin" => moduleValues.extract[List[ZeppelinInterpreterModule]]
      }
    }
    new Application(vMap("uuId"), vMap("name"),
      vMap("user"), vMap("sourceDir"), vMap("cronExpr"), modules)
  }

  override def preStart() = {
    context.system.scheduler.schedule(1.seconds, 2000.millis, master, RestServerHeartBeat)
  }

  def actorRefFactory = context

  def receive = runRoute(route)
}

object test extends App {
  def internalModules(): String = {
    val a = HibernateUtil.executeQuery("select text as name,type,libPath,ownerId," +
      "params from TElementTplEntity", null).map(_.asInstanceOf[Array[Object]].mkString(",")).
      map(_.split(","))
    val b = Array[String]("name", "type", "libPath", "ownerId", "params")
    val tmpRes = a.map(b.zip(_).map(tup => "\"" + tup._1 + "\":\"" + tup._2 + "\""))
    val res = for (i <- tmpRes.indices) yield "\"data_" + i + "\":{" + tmpRes(i).mkString(",") +
      "}"
    "{" + res.mkString(",\n") + "}"
  }

  case class Winner(id: Long, numbers: List[Int]) {
    val a = 123
  }

  case class Lotto(
                    id: Long,
                    winningNumbers: List[Int],
                    winners: List[Winner],
                    drawDate: Option[java.util.Date])

  implicit val formats = DefaultFormats
  val t = parse(
    """{"type":"spark","uuId":"2101" ,"id":"133463","name":"PricePrediction",
      |"user":"bi_ttd_bigdata",
      |"modules":[{"user":"bi_ttd_bigdata","flowId":"133463","libId":"6","uuId":"0001","name":"liftChart",
      |"appUUId":"2101","memory":4,"cores":2,
      |"sparkArgs":{"master":"yarn-client","num-executors":"6","executor-memory":"5g","executor-cores":"4","driver-memory":"4g"},
      	 	  |"paramsMap":{"inputType":"PARQUET"},"inputDesc":"/home/bi_ttd_bigdata/zyy/di/dimianPrediction",
      	 	  |"outPath":"/home/bi_ttd_bigdata/zyy/di/res"}],"sourceDir":"/tmp/atom/job/predictPrice","cronExpr":"*/5 * * * * ?"}""".stripMargin)
  //    val modules = (t \ "modules").extract[List[claz]]
  val vMap = t.removeField(_._1 == "modules").values.asInstanceOf[Map[String, String]]
  val moduleValues = t \ "modules"
  val modules = {
    vMap("type") match {
      //            case "shell"=>println("shell")
      case "spark" => moduleValues.extract[List[SparkModule]]
      case "shell" => moduleValues.extract[List[ShellModule]]
    }
  }
  modules.foreach(m => println(m.getField("cores")))
  //    val app = new Application(vMap("uuId"), vMap("id"), vMap("name"),
  //        vMap("user"), vMap("sourceDir"), vMap("cronExpr"), modules)
  //    println(app.toString)
}
