package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author juntao zhang
 */
@Entity
@Table(name = "t_element_tpl_group")
public class ElementTplGroup extends BaseEntity {
    private String name;
    private String value;
    private Boolean closed;
    private Integer sort;

    public ElementTplGroup() {
    }

    public ElementTplGroup(String name, String value, Boolean closed, Integer sort) {
        this.name = name;
        this.value = value;
        this.closed = closed;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public ElementTplGroup setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ElementTplGroup setValue(String value) {
        this.value = value;
        return this;
    }

    public Boolean getClosed() {
        return closed;
    }

    public ElementTplGroup setClosed(Boolean closed) {
        this.closed = closed;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public ElementTplGroup setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
}
