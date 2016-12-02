package com.ctrip.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author minglei
 */
@Entity
@Table(name = "t_element_history")
public class ElementHistory extends AbstractElement {
//	private Long startTime;
//	private Long endTime;
//	private String cronId;
	private String runtimeId;
	private Long elementId;
	private List<ElementParamHistory> params;

//	public Long getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(Long startTime) {
//		this.startTime = startTime;
//	}
//
//	public Long getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Long endTime) {
//		this.endTime = endTime;
//	}

//	public String getCronId() {
//		return cronId;
//	}
//
//	public void setCronId(String cronId) {
//		this.cronId = cronId;
//	}
//	
	public String getRuntimeId() {
		return runtimeId;
	}

	public void setRuntimeId(String runtimeId) {
		this.runtimeId = runtimeId;
	}

	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
		this.elementId = elementId;
	}

	@Transient
	public List<ElementParamHistory> getParams() {
		return params;
	}

	public ElementHistory setParams(List<ElementParamHistory> params) {
		this.params = params;
		return this;
	}

}
