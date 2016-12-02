package com.ctrip.persistence.entity;

import com.ctrip.persistence.enums.ElementTplParamValueType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author jtzhang
 */
@Entity
@Table(name = "t_element_tpl_param")
public class ElementTplParam extends BaseEntity {
    private Long elementTplId;
    private String name;
    private String defaultValue;
    private String inputType;
    private ElementTplParamValueType valueType = ElementTplParamValueType.STRING;
    private Long groupId;
//    private String comments;

    public ElementTplParam() {
    }

    public ElementTplParam(Long elementTplId, String name, String defaultValue, String inputType,
        Long groupId, String comments) {
        this.elementTplId = elementTplId;
        this.name = name;
        this.defaultValue = defaultValue;
        this.inputType = inputType;
        this.groupId = groupId;
     //   this.comments = comments;
    }

    public Long getElementTplId() {
        return elementTplId;
    }

    public ElementTplParam setElementTplId(Long elementTplId) {
        this.elementTplId = elementTplId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ElementTplParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ElementTplParam setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

//    public String getComments() {
//        return comments;
//    }
//
//    public ElementTplParam setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }

    public Long getGroupId() {
        return groupId;
    }

    public ElementTplParam setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getInputType() {
        return inputType;
    }

    public ElementTplParam setInputType(String inputType) {
        this.inputType = inputType;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public ElementTplParamValueType getValueType() {
        return valueType;
    }

    public ElementTplParam setValueType(ElementTplParamValueType valueType) {
        this.valueType = valueType;
        return this;
    }
}
