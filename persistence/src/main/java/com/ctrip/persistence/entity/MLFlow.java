package com.ctrip.persistence.entity;

import java.util.List;

import javax.persistence.*;

import com.ctrip.persistence.enums.MLFlowStatus;

/**
 * @author jtzhang
 */
@Entity
@Table(name = "t_ml_flow")
public class MLFlow extends AbstractMLFlow {
	private List<Element> elements;
	private List<Link> links;
    private MLFlowStatus status;
    private String latestRuntimeId;
    @Transient
    public List<Element> getElements() {
        return elements;
    }

    public MLFlow setElements(List<Element> elements) {
        this.elements = elements;
        return this;
    }
    
    @Transient
    public List<Link> getLinks() {
        return links;
    }

    public MLFlow setLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public MLFlowStatus getStatus() {
        return status;
    }

    public AbstractMLFlow setStatus(MLFlowStatus status) {
        this.status = status;
        return this;
    }
    
	public String getLatestRuntimeId() {
		return latestRuntimeId;
	}

	public void setLatestRuntimeId(String latestRuntimeId) {
		this.latestRuntimeId = latestRuntimeId;
	}
	
}
