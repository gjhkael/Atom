package com.ctrip.persistence.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @javax.persistence.Table(name = "t_element", schema = "", catalog = "test") public class TElementEntity {
    private long id;

    @Id @javax.persistence.Column(name = "id") public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Timestamp createdDate;

    @Basic @javax.persistence.Column(name = "created_date") public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    private Timestamp lastModified;

    @Basic @javax.persistence.Column(name = "last_modified") public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    private int version;

    @Basic @javax.persistence.Column(name = "version") public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private String uuid;

    @Basic @javax.persistence.Column(name = "uuid") public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private int height;

    @Basic @javax.persistence.Column(name = "height") public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width;

    @Basic @javax.persistence.Column(name = "width") public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private double positionx;

    @Basic @javax.persistence.Column(name = "positionx") public double getPositionx() {
        return positionx;
    }

    public void setPositionx(double positionx) {
        this.positionx = positionx;
    }

    private double positiony;

    @Basic @javax.persistence.Column(name = "positiony") public double getPositiony() {
        return positiony;
    }

    public void setPositiony(double positiony) {
        this.positiony = positiony;
    }

    private long mlFlowId;

    @Basic @javax.persistence.Column(name = "ml_flow_id") public long getMlFlowId() {
        return mlFlowId;
    }

    public void setMlFlowId(long mlFlowId) {
        this.mlFlowId = mlFlowId;
    }

    private long elementTplId;

    @Basic @javax.persistence.Column(name = "element_tpl_id") public long getElementTplId() {
        return elementTplId;
    }

    public void setElementTplId(long elementTplId) {
        this.elementTplId = elementTplId;
    }

    private String status;

    @Basic @javax.persistence.Column(name = "status") public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String latestRuntimeId;

    @Basic @javax.persistence.Column(name = "latest_runtime_id")
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

        TElementEntity that = (TElementEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (height != that.height)
            return false;
        if (width != that.width)
            return false;
        if (Double.compare(that.positionx, positionx) != 0)
            return false;
        if (Double.compare(that.positiony, positiony) != 0)
            return false;
        if (mlFlowId != that.mlFlowId)
            return false;
        if (elementTplId != that.elementTplId)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (latestRuntimeId != null ?
            !latestRuntimeId.equals(that.latestRuntimeId) :
            that.latestRuntimeId != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + height;
        result = 31 * result + width;
        temp = Double.doubleToLongBits(positionx);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(positiony);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (mlFlowId ^ (mlFlowId >>> 32));
        result = 31 * result + (int) (elementTplId ^ (elementTplId >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (latestRuntimeId != null ? latestRuntimeId.hashCode() : 0);
        return result;
    }
}
