package com.ctrip.atom.deploy.Job

import com.ctrip.atom.Logging
import com.ctrip.atom.deploy.TypeDefined.APP
import org.quartz.{DisallowConcurrentExecution, Job, JobExecutionContext}

import scala.collection.mutable

/**
 * Created by huang_xw on 2016/3/28.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@DisallowConcurrentExecution
class AppScheduleJob extends Job with Logging {
    override def execute(context: JobExecutionContext): Unit = {
        try {
            val app: Application = context.getMergedJobDataMap.get("app").asInstanceOf[Application]
            val appsNeedToScheduled: mutable.SynchronizedQueue[APP] = context.getMergedJobDataMap.
              get("appsNeedToScheduled").asInstanceOf[mutable.SynchronizedQueue[APP]]
            val idToApp = context.getMergedJobDataMap.get("idToApp").
              asInstanceOf[mutable.HashMap[String, Application]]
            logInfo(s"Quartz job triggered for application:${app.uuId}-${app.name}")
            while (idToApp.contains(app.scheduledID) || !appsNeedToScheduled.forall(_.scheduledID !=
              app.scheduledID)) {
                try {
                    logInfo(s"Application-${app.scheduledID} state:${idToApp(app.uuId).state}")
                } catch {
                    case _: Exception =>
                }
                logWaring(s"app:${app.scheduledID}--${app.name} is scheduling,retry after 20 " +
                  s"seconds!")
                Thread.sleep(20 * 1000)
            }
            val qJob = app.generateQJob()
            logInfo(s"\n\tCurrent dispatch list size is ${appsNeedToScheduled.size}" +
              s"\n\tDispatch application ${app.uuId}--${app.name} for ${app.quartzTimes} times" +
              s"\n\tAdd new application(${app.scheduledID}) in dispatch list ")
            appsNeedToScheduled.enqueue(qJob)
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }
}
