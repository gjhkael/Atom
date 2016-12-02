package com.ctrip.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author jtzhang
 */
@Entity
@Table(name = "t_element_tpl")
public class ElementTpl extends BaseEntity {
    /*js 组件type*/
    private String type;
    /*label名称*/
    private String text;
    private int height = 20;
    private int width = 20;
//    private Boolean isStart = false;
//    private Boolean isEnd = false;
//    private String inPorts;
//    private String outPorts;
    private Long groupId;
    private Long ownerId;
//    private String comments;
    //===============瞬时态===============
    private String code;
    private List<ElementTplParam> params;


    public ElementTpl() {
    }

    public ElementTpl(String type, String text, int height, int width, Boolean isStart,
        Boolean isEnd, String inPorts, String outPorts, Long groupId, String comments) {
        this.type = type;
        this.text = text;
        this.height = height;
        this.width = width;
//        this.isStart = isStart;
//        this.isEnd = isEnd;
//        this.inPorts = inPorts;
//        this.outPorts = outPorts;
        this.groupId = groupId;
//        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public ElementTpl setType(String type) {
        this.type = type;
        return this;
    }

    public String getText() {
        return text;
    }

    public ElementTpl setText(String text) {
        this.text = text;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ElementTpl setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public ElementTpl setWidth(int width) {
        this.width = width;
        return this;
    }

//    public Boolean getIsStart() {
//        return isStart;
//    }
//
//    public ElementTpl setIsStart(Boolean start) {
//        isStart = start;
//        return this;
//    }
//
//    public Boolean getIsEnd() {
//        return isEnd;
//    }
//
//    public ElementTpl setIsEnd(Boolean end) {
//        isEnd = end;
//        return this;
//    }
//
//    public String getInPorts() {
//        return inPorts;
//    }
//
//    public ElementTpl setInPorts(String inPorts) {
//        this.inPorts = inPorts;
//        return this;
//    }
//
//    public String getOutPorts() {
//        return outPorts;
//    }
//
//    public ElementTpl setOutPorts(String outPorts) {
//        this.outPorts = outPorts;
//        return this;
//    }

//    public String getComments() {
//        return comments;
//    }
//
//    public ElementTpl setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }

    public Long getGroupId() {
        return groupId;
    }

    public ElementTpl setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public ElementTpl setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Transient
    public String getCode() {
        return code;
    }

    public ElementTpl setCode(String code) {
        this.code = code;
        return this;
    }

    @Transient
    public List<ElementTplParam> getParams() {
        return params;
    }

    public ElementTpl setParams(List<ElementTplParam> params) {
        this.params = params;
        return this;
    }
}
