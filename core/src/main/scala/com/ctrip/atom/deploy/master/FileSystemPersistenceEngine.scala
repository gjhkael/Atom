package com.ctrip.atom.deploy.master

import java.io.{File, FileOutputStream}

import akka.serialization.Serialization
import com.ctrip.atom.Logging
import com.ctrip.atom.util.Utils

/**
 * Created by huang_xw on 2016/2/4.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
private[deploy] class FileSystemPersistenceEngine(
    val dir: String,
    val serialization: Serialization)
  extends PersistenceEngine with Logging {

    new File(dir).mkdir()

    override def persist(name: String, obj: Object): Unit = {
        serializeIntoFile(new File(dir + File.separator + name), obj)
    }

    override def unPersist(name: String): Unit = {
        new File(dir + File.separator + name).delete()
    }

    private def serializeIntoFile(file: File, value: AnyRef) {
        val created = file.createNewFile()
        if (!created) {
            throw new IllegalStateException("Could not create file: " + file)
        }
        val serializer = serialization.findSerializerFor(value)
        val serialized = serializer.toBinary(value)
        val out = new FileOutputStream(file)
        Utils.tryWithSafeFinally {
            out.write(serialized)
        } {
            out.close()
        }
    }
}
