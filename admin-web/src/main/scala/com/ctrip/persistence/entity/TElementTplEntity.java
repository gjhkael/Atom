package com.ctrip.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huang_xw on 2016/5/3.
 * E_mail:huang_xw@ctrip.com
 * web:www.ctrip.com
 */
@Entity @Table(name = "t_element_tpl", schema = "", catalog = "test") public class TElementTplEntity {
    private long id;
    private Timestamp createdDate;
    private Timestamp lastModified;
    private int version;
    private String type;
    private String text;
    private int height;
    private int width;
    private long groupId;
    private Long ownerId;

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

    @Basic @Column(name = "type") public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic @Column(name = "text") public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic @Column(name = "height") public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic @Column(name = "width") public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Basic @Column(name = "group_id") public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Basic @Column(name = "owner_id") public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TElementTplEntity that = (TElementTplEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (height != that.height)
            return false;
        if (width != that.width)
            return false;
        if (groupId != that.groupId)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null)
            return false;
        if (text != null ? !text.equals(that.text) : that.text != null)
            return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + height;
        result = 31 * result + width;
        result = 31 * result + (int) (groupId ^ (groupId >>> 32));
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        return result;
    }
}
