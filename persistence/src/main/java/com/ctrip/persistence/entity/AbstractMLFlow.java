package com.ctrip.persistence.entity;

import com.ctrip.persistence.enums.MLFlowStatus;

import javax.persistence.*;
import java.util.List;

/**
 * @author minglei
 */
@MappedSuperclass
public class AbstractMLFlow extends BaseEntity {
    private Long ownerId;
    private String name;

//    private String comments;
    private String cronexpression;
	//===============瞬时态===============
    private User owner;
  //  private List<AbstractElement> elements;
 //   private List<Link> links;


    public AbstractMLFlow from(AbstractMLFlow flow) {
        this.setOwnerId(flow.getOwnerId()).setName(flow.getName())
                .setCronexpression(flow.getCronexpression()).setId(flow.getId());
        return this;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public AbstractMLFlow setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getName() {
        return name;
    }

    public AbstractMLFlow setName(String name) {
        this.name = name;
        return this;
    }


//    public String getComments() {
//        return comments;
//    }
//
//    public AbstractMLFlow setComments(String comments) {
//        this.comments = comments;
//        return this;
//    }
//    
    public String getCronexpression() {
		return cronexpression;
	}

	public AbstractMLFlow setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
		return this;
	}


//    @Transient
//    public List<AbstractElement> getElements() {
//        return elements;
//    }
//
//    public AbstractMLFlow setElements(List<AbstractElement> elements) {
//        this.elements = elements;
//        return this;
//    }

//    @Transient
//    public List<Link> getLinks() {
//        return links;
//    }
//
//    public AbstractMLFlow setLinks(List<Link> links) {
//        this.links = links;
//        return this;
//    }

    @Transient
    public User getOwner() {
        return owner;
    }

    public AbstractMLFlow setOwner(User owner) {
        this.owner = owner;
        return this;
    }
}
