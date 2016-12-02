package com.ctrip.atom

import java.util.UUID

import scalaj.http.{Http, HttpOptions}

/**
 * Created by huang_xw on 2016/6/14.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
object StressTesting extends Logging {

    def getPostString: String = {
        val appUid = UUID.randomUUID()
        s"""{
              "id": "31326209",
              "sourceDir": "/tmp/atom/job/TestMetadata",
              "cronExpr": "",
              "uuId": "$appUid",
              "name": "TestMetadata",
              "modules": [{
                  "uuId": "${UUID.randomUUID()}",
                  "libId": "1",
                  "memory": 4,
                  "cores": 2,
                  "id": "34930692",
                  "sparkArgs": {
                      "num-executors": "20",
                      "executor-memory": "4g",
                      "executor-cores": "4",
                      "driver-memory": "4g",
                      "master": "yarn-client"
                  },
                  "paramsMap": {
                      "outdb": "tmp_ttd_bigdata",
                      "percentile_items": "0.01,0.05,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5,0.55,0.6,0.65,0.7,0.75,0.8,0.85,0.9,0.95,0.99",
                      "outPath": "/home/bi_ttd_bigdata/zyy/di/",
                      "partition": "",
                      "inputType": "HIVE",
                      "columns": "*",
                      "inputDesc": "tmp_ttd_bigdata.tmp_upliftmodel_train_control",
                      "percentile_columns": "*",
                      "groups": ""
                  },
                  "dependencies": ["00000"],
                  "jar": "atom-mllib_2.10-0.10.jar",
                  "mainClass": "com.ctrip.atom.ml.toolimpl.Metadata",
                  "name": "metadata",
                  "user": "bi_ttd_bigdata",
                  "flowId": "31326209",
                  "appUUId": "$appUid"
              }],
              "user": "bi_ttd_bigdata"
          }"""
    }

    def postRegister(times: Int): Unit = {
        for (i <- 0 until times) {
            Thread.sleep(2000)
            val result = Http("http://10.8.84.191:8089/job/register").postData(getPostString)
              .header("Content-Type", "application/json")
              .header("Charset", "UTF-8")
              .option(HttpOptions.readTimeout(10000)).asString
            logInfo(s"Register app $i--${result.body}")
        }
    }

    def main(args: Array[String]) {
        (0 until 10).foreach { i =>
            postRegister(50)
            Thread.sleep(600000)
        }
    }
}
