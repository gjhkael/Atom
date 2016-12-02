package com.ctrip.atom

/**
 * Created by huang_xw on 2016/2/14.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
class DiException(message: String, cause: Throwable) extends Exception(message, cause) {

    def this(message: String) = this(message, null)

}

case class RegisterException(
    message: String,
    cause: Throwable) extends DiException(message, cause) {
    def this(message: String) = this(message, null)
}
