package com.ctrip.atom.deploy.rest

/**
 * Created by huang_xw on 2016/4/22.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */

/**
 * 其修饰的trait，class只能在当前文件里面被继承
 * 用sealed修饰这样做的目的是告诉scala编译器在检查模式匹配的时候，让scala知道这些case的所有情况，
 * scala就能够在编译的时候进行检查，看你写的代码是否有没有漏掉什么没case到，减少编程的错误。
 */
sealed trait RestMessage extends Serializable

object RestMessage {

    case class CommonStatus(
        runningModules: Int,
        runningFlows: Int,
        totalModules: Int,
        totalFlows: Int,
        totalModules_lastWeek: Int,
        totalFlows_lastWeek: Int,
        averageTime: Float) extends RestMessage

    case class GetFieldFromModule(moduleId: String, field: String)

}
