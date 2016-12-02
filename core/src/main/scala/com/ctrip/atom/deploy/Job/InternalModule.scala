package com.ctrip.atom.deploy.Job

/**
 * Created by huang_xw on 2016/2/25.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
case class InternalModule(
    id: String,
    name: String,
    mainClass: String,
    arguments: Seq[String],
    mainJar: String,
    libPathEntries: List[String] = List[String]()) extends Serializable
