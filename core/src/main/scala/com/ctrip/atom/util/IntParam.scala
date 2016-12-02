package com.ctrip.atom.util

/**
 * Created by huang_xw on 2016/2/15.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[atom] object IntParam {
    def unapply(str: String): Option[Int] = {
        try {
            Some(str.toInt)
        } catch {
            case e: NumberFormatException => None
        }
    }
}
