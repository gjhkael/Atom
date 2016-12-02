package com.ctrip.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author minglei
 */
@Entity
@Table(name = "t_ml_flow_history")
public class MLFlowHistory extends AbstractMLFlow {
    private Long mlFlowId;
//    private Long startTime;
//    private Long endTime;
//    private String result;
//	private String cronId;
	private String runtimeId;
    
    private List<ElementHistory> elements;
    private List<LinkHistory> links;
    public Long getMlFlowId() {
        return mlFlowId;
    }

    public MLFlowHistory setMlFlowId(Long mlFlowId) {
        this.mlFlowId = mlFlowId;
        return this;
    }

//    public Long getStartTime() {
//        return startTime;
//    }
//
//    public MLFlowHistory setStartTime(Long startTime) {
//        this.startTime = startTime;
//        return this;
//    }
//
//    public Long getEndTime() {
//        return endTime;
//    }
//
//    public MLFlowHistory setEndTime(Long endTime) {
//        this.endTime = endTime;
//        return this;
//    }
//
//    public String getResult() {
//        return result;
//    }
//
//    public MLFlowHistory setResult(String result) {
//        this.result = result;
//        return this;
//    }

//    public String getCronId() {
//		return cronId;
//	}
//
//	public void setCronId(String cronId) {
//		this.cronId = cronId;
//	}

	public String getRuntimeId() {
		return runtimeId;
	}

	public void setRuntimeId(String runtimeId) {
		this.runtimeId = runtimeId;
	}


    @Transient
    public List<ElementHistory> getElements() {
        return elements;
    }

    public MLFlowHistory setElements(List<ElementHistory> elements) {
        this.elements = elements;
        return this;
    }
    
    @Transient
    public List<LinkHistory> getLinks() {
        return links;
    }

    public MLFlowHistory setLinks(List<LinkHistory> links) {
        this.links = links;
        return this;
    }
}
