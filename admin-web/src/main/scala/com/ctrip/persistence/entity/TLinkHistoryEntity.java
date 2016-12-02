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
@Entity @javax.persistence.Table(name = "t_link_history", schema = "", catalog = "test") public class TLinkHistoryEntity {
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

    private long sourceId;

    @Basic @javax.persistence.Column(name = "source_id") public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    private String sourceUuid;

    @Basic @javax.persistence.Column(name = "source_uuid") public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
    }

    private String sourcePort;

    @Basic @javax.persistence.Column(name = "source_port") public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    private String targetUuid;

    @Basic @javax.persistence.Column(name = "target_uuid") public String getTargetUuid() {
        return targetUuid;
    }

    public void setTargetUuid(String targetUuid) {
        this.targetUuid = targetUuid;
    }

    private long targetId;

    @Basic @javax.persistence.Column(name = "target_id") public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    private String targetPort;

    @Basic @javax.persistence.Column(name = "target_port") public String getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }

    private String type;

    @Basic @javax.persistence.Column(name = "type") public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private long mlFlowId;

    @Basic @javax.persistence.Column(name = "ml_flow_id") public long getMlFlowId() {
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

        TLinkHistoryEntity that = (TLinkHistoryEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (sourceId != that.sourceId)
            return false;
        if (targetId != that.targetId)
            return false;
        if (mlFlowId != that.mlFlowId)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;
        if (sourceUuid != null ? !sourceUuid.equals(that.sourceUuid) : that.sourceUuid != null)
            return false;
        if (sourcePort != null ? !sourcePort.equals(that.sourcePort) : that.sourcePort != null)
            return false;
        if (targetUuid != null ? !targetUuid.equals(that.targetUuid) : that.targetUuid != null)
            return false;
        if (targetPort != null ? !targetPort.equals(that.targetPort) : that.targetPort != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (int) (sourceId ^ (sourceId >>> 32));
        result = 31 * result + (sourceUuid != null ? sourceUuid.hashCode() : 0);
        result = 31 * result + (sourcePort != null ? sourcePort.hashCode() : 0);
        result = 31 * result + (targetUuid != null ? targetUuid.hashCode() : 0);
        result = 31 * result + (int) (targetId ^ (targetId >>> 32));
        result = 31 * result + (targetPort != null ? targetPort.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (mlFlowId ^ (mlFlowId >>> 32));
        return result;
    }
}
