package com.ctrip.atom.master

import java.util.Date

import com.ctrip.atom.{DiConf, AtomFunSuite}
import com.ctrip.atom.deploy.Job.SparkModule
import com.ctrip.atom.deploy.master.{MasterArguments, Master}

/**
 * Created by huang_xw on 2016/4/18.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class MaterSuit extends AtomFunSuite {
    var master =null
    def startMaster: Unit ={
        Master.startMaster(Array[String]())
    }
    test("registerModule") {
        startMaster
        val module = new SparkModule("aa", "123", "123", "123", "123", null, "123",
            12, 12, null, null)

//        master.registerModule(module)
    }
}
