package com.ctrip.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "t_flow_history", schema = "", catalog = "test")
@IdClass(TFlowHistoryEntityPK.class) public class TFlowHistoryEntity {
    private String uuid;
    private int scheduleTimes;
    private String id;
    private String name;
    private String owner;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private String modules;
    private String cronexpr;

    @Id @Column(name = "uuid") public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Id @Column(name = "schedule_times") public int getScheduleTimes() {
        return scheduleTimes;
    }

    public void setScheduleTimes(int scheduleTimes) {
        this.scheduleTimes = scheduleTimes;
    }

    @Basic @Column(name = "id") public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic @Column(name = "name") public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic @Column(name = "owner") public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Basic @Column(name = "start_time") public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic @Column(name = "end_time") public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic @Column(name = "status") public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic @Column(name = "modules") public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    @Basic @Column(name = "cronexpr") public String getCronexpr() {
        return cronexpr;
    }

    public void setCronexpr(String cronexpr) {
        this.cronexpr = cronexpr;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TFlowHistoryEntity that = (TFlowHistoryEntity) o;

        if (scheduleTimes != that.scheduleTimes)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
            return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (modules != null ? !modules.equals(that.modules) : that.modules != null)
            return false;
        if (cronexpr != null ? !cronexpr.equals(that.cronexpr) : that.cronexpr != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + scheduleTimes;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (modules != null ? modules.hashCode() : 0);
        result = 31 * result + (cronexpr != null ? cronexpr.hashCode() : 0);
        return result;
    }
}
