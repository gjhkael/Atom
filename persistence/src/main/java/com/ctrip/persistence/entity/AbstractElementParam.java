package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * @author jtzhang
 */
@MappedSuperclass
public class AbstractElementParam extends BaseEntity {
    private Long mlFlowId;
    private Long elementId;
    private Long elementTplParamId;
    private String value;
 //   private String comments;

    public AbstractElementParam from(AbstractElementParam param) {
        this.setMlFlowId(param.getMlFlowId()).setElementId(param.getElementId())
            .setElementTplParamId(param.getElementTplParamId()).setValue(param.getValue())
            .setId(param.getId());
        return this;
    }

    public Long getElementId() {
        return elementId;
    }

    public AbstractElementParam setElementId(Long elementId) {
        this.elementId = elementId;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AbstractElementParam setValue(String value) {
        this.value = value;
        return this;
    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public AbstractElementParam setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }

    public Long getElementTplParamId() {
        return elementTplParamId;
    }

    public AbstractElementParam setElementTplParamId(Long elementTplParamId) {
        this.elementTplParamId = elementTplParamId;
        return this;
    }

    public Long getMlFlowId() {
        return mlFlowId;
    }

    public AbstractElementParam setMlFlowId(Long mlFlowId) {
        this.mlFlowId = mlFlowId;
        return this;
    }
}
