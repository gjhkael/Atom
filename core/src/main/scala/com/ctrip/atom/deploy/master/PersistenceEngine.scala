package com.ctrip.atom.deploy.master

/**
 * Created by huang_xw on 2016/2/4.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
abstract class PersistenceEngine {
    /**
     * Define how the object is serialized and persisted.Implementation will depend
     * on the store used.
     */
    def persist(name: String, obj: Object)

    /**
     * Defines how the object referred by its name is removed frm the store
     */
    def unPersist(name: String)
}
