package com.ctrip.atom.lib

import com.ctrip.persistence.entity.TModuleHistoryEntity
import org.hibernate.cfg.Configuration
import org.hibernate.{Query, Session, SessionFactory, Transaction}

import scala.reflect.ClassTag

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
object HibernateUtil extends Logging {
    def main(args: Array[String]) {
        val a = new TModuleHistoryEntity()
        save[TModuleHistoryEntity](a)
    }


    private var sessionFactory: SessionFactory = null
    //使用线程局部模式
    val threadLocal: ThreadLocal[Session] = new ThreadLocal[Session]()
    sessionFactory = new Configuration().configure().addPackage("entity").buildSessionFactory()

    //获取全新的全新的sesession
    def openSession: Session = {
        sessionFactory.openSession()
    }

    //获取和线程关联的session
    def getCurrentSession: Session = {
        var session: Session = threadLocal.get()
        //判断是否得到
        if (session == null) {
            session = sessionFactory.openSession()
            //把session对象设置到 threadLocal,相当于该session已经和线程绑定
            threadLocal.set(session)
        }
        session
    }

    //统一的一个修改和删除(批量 hql) hql"delete upate ...??"
    def executeUpdate(hql: String, parameters: Array[String]) {
        var s: Session = null
        var tx: Transaction = null
        try {
            s = openSession
            tx = s.beginTransaction()
            val query: Query = s.createQuery(hql)
            //先判断是否有参数要绑定
            if (parameters != null && parameters.length > 0) {
                for (i <- parameters.indices) {
                    query.setString(i, parameters(i))
                }
            }
            query.executeUpdate()
            tx.commit()
        } catch {
            case e: Exception => e.printStackTrace()
                throw new RuntimeException(e.getMessage)
            // TODO: handle exception
        } finally {
            if (s != null && s.isOpen) {
                s.close()
            }
        }
    }

    //这里提供一个根据id返回对象的方法
    def findById[T: ClassTag](clazz: Class[T], id: java.io.Serializable): T = {
        var s: Session = null
        var tx: Transaction = null
        var obj: T = null.asInstanceOf[T]
        try {
            s = openSession
            tx = s.beginTransaction()
            obj = s.load(clazz, id).asInstanceOf[T]
            tx.commit()
        } catch {
            case e: Exception => e.printStackTrace()
                throw new RuntimeException(e.getMessage)
            // TODO: handle exception
        } finally {
            if (s != null && s.isOpen) {
                s.close()
            }
        }
        obj
    }

    //统一的添加的方法
    def save[T: ClassTag](obj: T) {
        var s: Session = null
        var tx: Transaction = null
        try {
            s = openSession
            tx = s.beginTransaction()
            s.save(obj)
            tx.commit()
        } catch {
            case e: Exception =>
                if (tx != null) {
                    tx.rollback()
                }
                throw new RuntimeException(e.getMessage);
        } finally {
            if (s != null && s.isOpen) {
                s.close()
            }
        }
    }

    def saveOrUpdate[T: ClassTag](obj: T): Unit = {
        var s: Session = null
        var tx: Transaction = null
        try {
            s = openSession
            tx = s.beginTransaction()
            s.saveOrUpdate(obj)
            tx.commit()
        } catch {
            case e: Exception =>
                if (tx != null) {
                    tx.rollback()
                }
                throw new RuntimeException(e.getMessage);
            // TODO: handle exception
        } finally {
            if (s != null && s.isOpen) {
                s.close()
            }
        }
    }


    //提供一个统一的查询方法(带分页) hql 形式 from 类  where 条件=? ..
    def executeQueryByPage(
        hql: String,
        parameters: Array[String],
        pageSize: Int,
        pageNow: Int): List[_] = {
        var s: Session = null
        var list: List[_] = null
        try {
            s = openSession
            val query: Query = s.createQuery(hql)
            //先判断是否有参数要绑定
            if (parameters != null && parameters.length > 0) {
                for (i <- parameters.indices) {
                    query.setString(i, parameters(i))
                }
            }
            query.setFirstResult((pageNow - 1) * pageSize).setMaxResults(pageSize)
            list = query.list.toArray.toList
        } catch {
            case e: Exception => e.printStackTrace()
                throw new RuntimeException(e.getMessage)
            // TODO: handle exception
        } finally {
            if (s != null && s.isOpen) {
                s.disconnect()
            }
        }
        list
    }

    //提供一个统一的查询方法 hql 形式 from 类  where 条件=? ..
    def executeQuery(hql: String, parameters: Array[String]) {
        var s: Session = null
        var list: List[_] = null
        try {
            s = openSession
            val query: Query = s.createQuery(hql)
            //先判断是否有参数要绑定
            if (parameters != null && parameters.length > 0) {
                for (i <- parameters.indices) {
                    query.setString(i, parameters(i))
                }
            }
            list = query.list().toArray.toList
        } catch {
            case e: Exception => e.printStackTrace()
                throw new RuntimeException(e.getMessage)
            // TODO: handle exception
        } finally {
            if (s != null && s.isOpen) {
                s.close()
            }
        }
        list
    }

    //提供一个统一的查询方法 总是返回一个对象
    def uniqueQuery[T: ClassTag](hql: String, parameters: Array[String]): T = {
        var s: Session = null
        var obj: T = null.asInstanceOf[T]
        try {
            s = openSession
            val query: Query = s.createQuery(hql)
            //先判断是否有参数要绑定
            if (parameters != null && parameters.length > 0) {
                for (i <- parameters.indices) {
                    query.setString(i, parameters(i))
                }
            }
            obj = query.uniqueResult().asInstanceOf[T]
        } catch {
            case e: Exception => e.printStackTrace()
                throw new RuntimeException(e.getMessage)
            // TODO: handle exception
        } finally {
            if (s != null && s.isOpen) {
                s.close()
            }
        }
        obj
    }
}
