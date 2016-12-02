package com.ctrip.atom.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by huang_xw on 2016/4/20.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
public class TModuleHistoryEntityPK implements Serializable {
    private String uuid;
    private int scheduleTimes;

    @Column(name = "uuid") @Id public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Column(name = "schedule_times") @Id public int getScheduleTimes() {
        return scheduleTimes;
    }

    public void setScheduleTimes(int scheduleTimes) {
        this.scheduleTimes = scheduleTimes;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TModuleHistoryEntityPK that = (TModuleHistoryEntityPK) o;

        if (scheduleTimes != that.scheduleTimes)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + scheduleTimes;
        return result;
    }
}
