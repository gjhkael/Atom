package com.ctrip.atom.deploy.Job

import java.sql.Timestamp
import java.util.Date

import com.ctrip.atom.Logging
import com.ctrip.atom.deploy.Job.AppState.AppState
import com.ctrip.atom.deploy.TypeDefined.APP
import com.ctrip.atom.persistence.HibernateUtil
import com.ctrip.atom.persistence.entity.TFlowHistoryEntity
import org.quartz.Scheduler

import scala.collection.mutable.ListBuffer

/**
 * Created by huang_xw on 2016/1/29.
 * huang_xw@ctrip.com
 */
case class Application(
    uuId: String,
    name: String,
    user: String,
    sourceDir: String,
    cronExpr: String,
    modules: List[Module]) extends Logging {

    def generateQJob(): APP = {
        generateNewScheduleId()
        val qModules = modules.foldLeft(ListBuffer[Module]())((
        res, m) => res += m.generateQModule()).toList
        val qApp = new APP(uuId, name, user, sourceDir, cronExpr, qModules)
        qApp.isScheduled = false
        //logInfo("\n\tGenerating new Job for quartz job...")
        //logInfo(s"\n\tNew job is:${qApp.toString}")
        qApp.setScheduleTimes(quartzTimes)
        qApp
    }

    private var isScheduled = true
    @transient var state: AppState = AppState.WAITING
    @transient val appEntity = new TFlowHistoryEntity()
    val submitTime: Date = new Date()
    private var scheduler: Scheduler = null
    //触发执行次数
    @volatile var quartzTimes: Int = 0

    def init() {
        appEntity.setCronexpr(cronExpr)
        appEntity.setId("app")
        appEntity.setModules(modules.map(_.uuId).mkString(","))
        appEntity.setName(name)
        appEntity.setOwner(user)
        appEntity.setScheduleTimes(quartzTimes)
        appEntity.setStartTime(new Timestamp(System.currentTimeMillis()))
        appEntity.setStatus(state.toString)
        appEntity.setUuid(uuId)
    }

    def setScheduleTimes(times: Int): Unit = {
        this.quartzTimes = times
        appEntity.setScheduleTimes(times)
    }

    //    def save(): Unit = {
    //        HibernateUtil.save(appEntity)
    //    }

    init()

    def generateNewScheduleId() = {
        quartzTimes += 1
    }

    def scheduledID: String = {
        uuId + "_" + quartzTimes
    }

    def setScheduler(scheduler: Scheduler) = {
        this.scheduler = scheduler
    }

    def isScheduleJob: Boolean = {
        (!(cronExpr == null || cronExpr.isEmpty || cronExpr == "00000")) && isScheduled
    }

    /**
     * shutdown scheduler
     * @param waitForJobsToComplete If wait for running job to complete
     */
    def shutdown(waitForJobsToComplete: Boolean): Unit = {
        if (scheduler != null) {
            scheduler.shutdown(waitForJobsToComplete)
            scheduler.clear()
        } else {
        }
    }

    def clear(): Unit = {
        scheduler.clear()
    }

    override def toString: String = {
        ("applicationInfo===>\n\tuuId:%s\n\tname:%s\n\tuser:%s\n\tmodules:%s\n\tsubmitTime:%s\n" +
          "\tcronExpr:%s\n\tisScheduleJob:%s").
          format(uuId, name, user, modules.map(m => m.uuId).mkString(","),
              submitTime, cronExpr, isScheduleJob)
    }

    /**
     * Move or copy the description File to another folder when app state changed.
     * @param state State
     */
    def setState(state: AppState): Unit = {
        logInfo(s"Change app {$scheduledID} state to {${state.toString}}")
        this.synchronized {
            this.state = state
        }
        appEntity.setStatus(state.toString)
        if (AppState.isFinishApp(state)) {
            appEntity.setEndTime(new Timestamp(System.currentTimeMillis()))
            if (state == AppState.FAILED) {
                this.modules.foreach(m => if (m.state != ModuleState.RUNNING && !m.isFinished) m.
                  setState(ModuleState.FAILED))
            }
        }
        HibernateUtil.saveOrUpdate(appEntity)
    }
}
