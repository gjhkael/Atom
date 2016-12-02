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
@Entity @javax.persistence.Table(name = "t_zeppelin_paragraph", schema = "", catalog = "test")
public class TZeppelinParagraphEntity {
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

    private String noteId;

    @Basic @javax.persistence.Column(name = "note_id") public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    private String noteName;

    @Basic @javax.persistence.Column(name = "note_name") public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    private String jobName;

    @Basic @javax.persistence.Column(name = "job_name") public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    private Long elementId;

    @Basic @javax.persistence.Column(name = "element_id") public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    private String uuid;

    @Basic @javax.persistence.Column(name = "uuid") public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private String code;

    @Basic @javax.persistence.Column(name = "code") public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String msg;

    @Basic @javax.persistence.Column(name = "msg") public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String type;

    @Basic @javax.persistence.Column(name = "type") public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String status;

    @Basic @javax.persistence.Column(name = "status") public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String text;

    @Basic @javax.persistence.Column(name = "text") public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TZeppelinParagraphEntity that = (TZeppelinParagraphEntity) o;

        if (id != that.id)
            return false;
        if (version != that.version)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
            return false;
        if (lastModified != null ?
            !lastModified.equals(that.lastModified) :
            that.lastModified != null)
            return false;
        if (noteId != null ? !noteId.equals(that.noteId) : that.noteId != null)
            return false;
        if (noteName != null ? !noteName.equals(that.noteName) : that.noteName != null)
            return false;
        if (jobName != null ? !jobName.equals(that.jobName) : that.jobName != null)
            return false;
        if (elementId != null ? !elementId.equals(that.elementId) : that.elementId != null)
            return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null)
            return false;
        if (code != null ? !code.equals(that.code) : that.code != null)
            return false;
        if (msg != null ? !msg.equals(that.msg) : that.msg != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (text != null ? !text.equals(that.text) : that.text != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (noteId != null ? noteId.hashCode() : 0);
        result = 31 * result + (noteName != null ? noteName.hashCode() : 0);
        result = 31 * result + (jobName != null ? jobName.hashCode() : 0);
        result = 31 * result + (elementId != null ? elementId.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
