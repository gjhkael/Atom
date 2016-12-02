package com.ctrip.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "t_element_tpl_param", schema = "", catalog = "test") public class TElementTplParamEntity {
    private long id;
    private Timestamp createdDate;
    private Timestamp lastModified;
    private int version;
    private long elementTplId;
    private String name;
    private String defaultValue;
    private String valueType;
    private String inputType;
    private long groupId;

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

    @Basic @Column(name = "element_tpl_id") public long getElementTplId() {
        return elementTplId;
    }

    public void setElementTplId(long elementTplId) {
        this.elementTplId = elementTplId;
    }

    @Basic @Column(name = "name") public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic @Column(name = "default_value") public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Basic @Column(name = "value_type") public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @Basic @Column(name = "input_type") public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    @Basic @Column(name = "group_id") public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TElementTplParamEntity that = (TElementTplParamEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (elementTplId != that.elementTplId)
            return false;
        if (groupId != that.groupId)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (defaultValue != null ?
            !defaultValue.equals(that.defaultValue) :
            that.defaultValue != null)
            return false;
        if (valueType != null ? !valueType.equals(that.valueType) : that.valueType != null)
            return false;
        if (inputType != null ? !inputType.equals(that.inputType) : that.inputType != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (int) (elementTplId ^ (elementTplId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (valueType != null ? valueType.hashCode() : 0);
        result = 31 * result + (inputType != null ? inputType.hashCode() : 0);
        result = 31 * result + (int) (groupId ^ (groupId >>> 32));
        return result;
    }
}
