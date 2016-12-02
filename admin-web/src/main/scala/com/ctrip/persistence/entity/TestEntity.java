package com.ctrip.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "Test", schema = "", catalog = "test") public class TestEntity {
    private String a;

    @Basic @Column(name = "a") public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TestEntity that = (TestEntity) o;

        if (a != null ? !a.equals(that.a) : that.a != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        return a != null ? a.hashCode() : 0;
    }
}
