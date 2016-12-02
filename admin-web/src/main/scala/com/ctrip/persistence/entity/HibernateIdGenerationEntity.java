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
@Entity @Table(name = "hibernate_id_generation", schema = "", catalog = "test")
public class HibernateIdGenerationEntity {
    private long nextHi;

    @Basic @Column(name = "next_hi") public long getNextHi() {
        return nextHi;
    }

    public void setNextHi(long nextHi) {
        this.nextHi = nextHi;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        HibernateIdGenerationEntity that = (HibernateIdGenerationEntity) o;

        if (nextHi != that.nextHi)
            return false;

        return true;
    }

    @Override public int hashCode() {
        return (int) (nextHi ^ (nextHi >>> 32));
    }
}
