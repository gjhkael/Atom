package com.ctrip.atom.persistence

import com.ctrip.atom.persistence.entity.TElementEntity

/**
 * Created by huang_xw on 2016/4/15.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
object Test {
    def main(args: Array[String]) {
        println(HibernateUtil.findById(classOf[TElementEntity], 19136512L).getId)
        //        val session = HibernateUtil.getCurrentSession
        //        session.beginTransaction()
        //        val query = session.createQuery("from TElementEntity")
        //        query.list().toArray.foreach(a=>println(a.asInstanceOf[TElementEntity].getId))
        //        session.close()
    }
}
