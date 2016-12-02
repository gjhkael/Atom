package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * @author jtzhang
 */
@MappedSuperclass
public class AbstractLink extends BaseEntity {
    private Long mlFlowId;
    private String uuid;
    private Long sourceId;
    private String sourcePort;
    private String sourceUuid;
    private Long targetId;
    private String targetPort;
    private String targetUuid;
    private String type;
//    private String comments;


    public AbstractLink from(AbstractLink link) {
        this.setMlFlowId(link.getMlFlowId()).setUuid(link.getUuid()).setSourceId(link.getSourceId())
            .setSourcePort(link.getSourcePort()).setSourceUuid(link.getSourceUuid())
            .setTargetId(link.getTargetId()).setTargetPort(link.getTargetPort())
            .setTargetUuid(link.getTargetUuid()).setType(link.getType())
            .setId(link.getId());
        return this;
    }

    public Long getMlFlowId() {
        return mlFlowId;
    }

    public AbstractLink setMlFlowId(Long mlFlowId) {
        this.mlFlowId = mlFlowId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public AbstractLink setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public AbstractLink setSourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public AbstractLink setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
        return this;
    }

    public String getSourceUuid() {
        return sourceUuid;
    }

    public AbstractLink setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public AbstractLink setTargetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public String getTargetPort() {
        return targetPort;
    }

    public AbstractLink setTargetPort(String targetPort) {
        this.targetPort = targetPort;
        return this;
    }

    public String getTargetUuid() {
        return targetUuid;
    }

    public AbstractLink setTargetUuid(String targetUuid) {
        this.targetUuid = targetUuid;
        return this;
    }

    public String getType() {
        return type;
    }

    public AbstractLink setType(String type) {
        this.type = type;
        return this;
    }

//    public String getComments() {
//        return comments;
//    }
//
//    public AbstractLink setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }

}
