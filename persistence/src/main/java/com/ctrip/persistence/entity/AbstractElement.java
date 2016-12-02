package com.ctrip.persistence.entity;

import com.ctrip.persistence.enums.MLFlowStatus;

import javax.persistence.*;
import java.util.List;

/**
 * @author jtzhang
 */
@MappedSuperclass
public class AbstractElement extends BaseEntity {
    private Long mlFlowId;
    private Long elementTplId;
    private String uuid;
   // private String text;
    private int height;
    private int width;
    private Double positionX;
    private Double positionY;
    //===============瞬时态===============
  //  private List<AbstractElementParam> params;

    public AbstractElement from(AbstractElement element) {
        this.setMlFlowId(element.getMlFlowId()).setElementTplId(element.getElementTplId())
            .setUuid(element.getUuid()).setHeight(element.getHeight())
            .setWidth(element.getWidth()).setPositionX(element.getPositionX())
            .setPositionY(element.getPositionY())
            .setId(element.getId());
        return this;

    }

    public Long getMlFlowId() {
        return mlFlowId;
    }

    public AbstractElement setMlFlowId(Long mlFlowId) {
        this.mlFlowId = mlFlowId;
        return this;
    }

    public Long getElementTplId() {
        return elementTplId;
    }

    public AbstractElement setElementTplId(Long elementTplId) {
        this.elementTplId = elementTplId;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public AbstractElement setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

//    public String getText() {
//        return text;
//    }
//
//    public AbstractElement setText(String text) {
//        this.text = text;
//        return this;
//    }

    public int getHeight() {
        return height;
    }

    public AbstractElement setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public AbstractElement setWidth(int width) {
        this.width = width;
        return this;
    }

    public Double getPositionX() {
        return positionX;
    }

    public AbstractElement setPositionX(Double positionX) {
        this.positionX = positionX;
        return this;
    }

    public Double getPositionY() {
        return positionY;
    }

    public AbstractElement setPositionY(Double positionY) {
        this.positionY = positionY;
        return this;
    }

//    public String getComments() {
//        return comments;
//    }
//
//    public AbstractElement setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }

//    @Transient
//    public List<AbstractElementParam> getParams() {
//        return params;
//    }
//
//    public AbstractElement setParams(List<AbstractElementParam> params) {
//        this.params = params;
//        return this;
//    }

}
