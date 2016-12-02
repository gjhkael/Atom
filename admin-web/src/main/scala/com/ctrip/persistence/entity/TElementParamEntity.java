package com.ctrip.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "t_element_param", schema = "", catalog = "test") public class TElementParamEntity {
    private long id;
    private Timestamp createdDate;
    private Timestamp lastModified;
    private int version;
    private long elementId;
    private String value;
    private long elementTplParamId;
    private long mlFlowId;

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

    @Basic @Column(name = "element_id") public long getElementId() {
        return elementId;
    }

    public void setElementId(long elementId) {
        this.elementId = elementId;
    }

    @Basic @Column(name = "value") public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic @Column(name = "element_tpl_param_id")
    public long getElementTplParamId() {
        return elementTplParamId;
    }

    public void setElementTplParamId(long elementTplParamId) {
        this.elementTplParamId = elementTplParamId;
    }

    @Basic @Column(name = "ml_flow_id") public long getMlFlowId() {
        return mlFlowId;
    }

    public void setMlFlowId(long mlFlowId) {
        this.mlFlowId = mlFlowId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TElementParamEntity that = (TElementParamEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (elementId != that.elementId)
            return false;
        if (elementTplParamId != that.elementTplParamId)
            return false;
        if (mlFlowId != that.mlFlowId)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (value != null ? !value.equals(that.value) : that.value != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (int) (elementId ^ (elementId >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (int) (elementTplParamId ^ (elementTplParamId >>> 32));
        result = 31 * result + (int) (mlFlowId ^ (mlFlowId >>> 32));
        return result;
    }
}
