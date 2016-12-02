package com.ctrip.atom.comet

import com.ctrip.atom.lib.{HibernateUtil, Logging}
import com.ctrip.persistence.entity.TFlowHistoryEntity
import net.liftweb.http.CometActor
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Schedule

import scala.xml.Text

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class DashBord extends CometActor with Logging {
    def totalFlows(): Long = {
        HibernateUtil.executeQueryByPage(
            "Select count(*) from TFlowHistoryEntity where uuid <> null",
            null,
            1000000, 1).head.asInstanceOf[Long]
    }

    def totalModules(): Long = {
        HibernateUtil.executeQueryByPage(
            "select count(*) from TModuleHistoryEntity where uuid <> null",
            null,
            1, 1).head.asInstanceOf[Long]
    }

    def render = bind("total_flows" -> "123")

    Schedule.schedule(this, GetCommon, 1000)

    override def lowPriority: PartialFunction[Any, Unit] = {
        case GetCommon =>
            println("Got message: GetCommon")
            partialUpdate(SetHtml("total_flows", Text(totalFlows().toString)))
            Schedule.schedule(this, GetCommon, 20000)
    }
}

case object GetCommon

object DashBord extends App {
    val totalEle = HibernateUtil.executeQueryByPage(
        "Select count(*) from TFlowHistoryEntity where uuid <> null",
        null, 1000, 1).asInstanceOf[List[TFlowHistoryEntity]]
    println(totalEle(0))
}
