package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jtzhang
 */
@Entity
@Table(name = "t_zeppelin_paragraph")
public class ZeppelinParagraph extends BaseEntity {
    private String noteId;
    private String noteName;
    private String jobName;
    private Long elementId;
    private String uuid;
    private String code;
    private String msg;
    private String type;
    private String status;
    private String text;

    public void from(ZeppelinParagraph p) {
        this.setNoteId(p.getNoteId()).setNoteName(p.getNoteName()).setJobName(p.getJobName())
            .setElementId(p.getElementId()).setUuid(p.getUuid()).setCode(p.getCode())
            .setMsg(p.getMsg()).setType(p.getType()).setStatus(p.getStatus()).setText(p.getText());
    }

    public String getNoteId() {
        return noteId;
    }

    public ZeppelinParagraph setNoteId(String noteId) {
        this.noteId = noteId;
        return this;
    }

    public String getNoteName() {
        return noteName;
    }

    public ZeppelinParagraph setNoteName(String noteName) {
        this.noteName = noteName;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public ZeppelinParagraph setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public Long getElementId() {
        return elementId;
    }

    public ZeppelinParagraph setElementId(Long elementId) {
        this.elementId = elementId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public ZeppelinParagraph setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ZeppelinParagraph setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ZeppelinParagraph setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getType() {
        return type;
    }

    public ZeppelinParagraph setType(String type) {
        this.type = type;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ZeppelinParagraph setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getText() {
        return text;
    }

    public ZeppelinParagraph setText(String text) {
        this.text = text;
        return this;
    }

}
