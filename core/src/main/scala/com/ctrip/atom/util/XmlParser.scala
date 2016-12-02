package com.ctrip.atom.util

import java.io.File

import com.ctrip.atom.util.Utils.getOrExit

import scala.xml.XML

/**
 * Created by huang_xw on 2016/1/29.
 * huang_xw@ctrip.com
 */
object XmlParser {
    def parseApp(xmlFile: File) = {
        val xmlData = XML.loadFile(xmlFile)
        val head = xmlData \ "head"
        val appParams = (
          getOrExit((head \ "id").text),
          getOrExit((head \ "uuId").text),
          getOrExit((head \ "name").text),
          getOrExit((head \ "user").text),
          (head \ "sourceDir").text,
          (head \ "cronExpr").text)
        val moduleParams = for (node <- xmlData \ "modules" \ "module") yield {
            ( getOrExit((node \ "libId").text),
              getOrExit((node \ "uuId").text),
              getOrExit((node \ "name").text),
              (node \ "dependencies" \\ "uuId").toList.map(_.text),
              (node \ "sparkArgs" \ "_").toList.map(arg => (arg.label, arg.text)).toMap,
              (node \ "jobParams" \ "_").toList.map(arg => (arg.label, arg.text)).toMap)
        }
        (appParams, moduleParams)
    }
}
