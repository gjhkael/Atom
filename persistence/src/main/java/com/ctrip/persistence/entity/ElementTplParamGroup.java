package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author juntao zhang
 */
@Entity
@Table(name = "t_element_tpl_param_group")
public class ElementTplParamGroup extends BaseEntity {
    private String name;
    private String value;
    private Boolean closed;
    private Integer sort;

    public ElementTplParamGroup() {
    }

    public ElementTplParamGroup(String name, String value, Boolean closed, Integer sort) {
        this.name = name;
        this.value = value;
        this.closed = closed;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public ElementTplParamGroup setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ElementTplParamGroup setValue(String value) {
        this.value = value;
        return this;
    }

    public Boolean getClosed() {
        return closed;
    }

    public ElementTplParamGroup setClosed(Boolean closed) {
        this.closed = closed;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public ElementTplParamGroup setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
}
