package com.ctrip.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "t_ml_flow", schema = "", catalog = "test") public class TMlFlowEntity {
    private long id;
    private Timestamp createdDate;
    private Timestamp lastModified;
    private int version;
    private long ownerId;
    private String name;
    private String status;
    private String cronexpression;
    private String latestRuntimeId;

    @Id @Column(name = "id") public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic @Column(name = "created_date") public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic @Column(name = "last_modified") public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    @Basic @Column(name = "version") public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Basic @Column(name = "owner_id") public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @Basic @Column(name = "name") public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic @Column(name = "status") public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic @Column(name = "cronexpression") public String getCronexpression() {
        return cronexpression;
    }

    public void setCronexpression(String cronexpression) {
        this.cronexpression = cronexpression;
    }

    @Basic @Column(name = "latest_runtime_id")
    public String getLatestRuntimeId() {
        return latestRuntimeId;
    }

    public void setLatestRuntimeId(String latestRuntimeId) {
        this.latestRuntimeId = latestRuntimeId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TMlFlowEntity that = (TMlFlowEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (ownerId != that.ownerId)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (cronexpression != null ?
            !cronexpression.equals(that.cronexpression) :
            that.cronexpression != null)
            return false;
        if (latestRuntimeId != null ?
            !latestRuntimeId.equals(that.latestRuntimeId) :
            that.latestRuntimeId != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (int) (ownerId ^ (ownerId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (cronexpression != null ? cronexpression.hashCode() : 0);
        result = 31 * result + (latestRuntimeId != null ? latestRuntimeId.hashCode() : 0);
        return result;
    }
}
