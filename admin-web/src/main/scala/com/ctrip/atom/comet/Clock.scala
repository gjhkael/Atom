package com.ctrip.atom.comet

import java.text.SimpleDateFormat
import java.util.Date

import com.ctrip.atom.lib.Logging
import net.liftweb.common.Full
import net.liftweb.http.CometActor
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Schedule

import scala.xml.Text

/**
 * Created by huang_xw on 2016/5/1.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class Clock extends CometActor with Logging {
    override def defaultPrefix = Full("clk")

    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm")

    def current = sdf.format(new Date())

    def render = bind("time" -> current)

    // schedule a ping every 10 seconds so we redraw
    Schedule.schedule(this, Tick, 3000)

    override def lowPriority: PartialFunction[Any, Unit] = {
        case Tick => {
            logInfo("Got tick " + new Date())
            partialUpdate(SetHtml("time", Text(current)))
            // schedule an update in 1 minute
            Schedule.schedule(this, Tick, 60000)
        }
    }
}

case object Tick
