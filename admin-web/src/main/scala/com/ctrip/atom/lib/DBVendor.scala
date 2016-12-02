package com.ctrip.atom.lib

import java.sql.{Connection, DriverManager}

import net.liftweb.common.{Box, Empty, Full}
import net.liftweb.db.{ConnectionIdentifier, ConnectionManager}

/**
 * Created by huang_xw on 2016/4/29.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
object DBVendor {

}

object MysqlVendor extends ConnectionManager {
    override def newConnection(name: ConnectionIdentifier): Box[Connection] = {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            val conn = DriverManager.getConnection("jdbc:mysql://10.8.84" +
              ".191/atom?user=root&password=root")
            Full(conn)
        } catch {
            case e: Exception => e.printStackTrace(); Empty
        }
    }

    def releaseConnection(conn: Connection): Unit = {
        conn.close()
    }
}
