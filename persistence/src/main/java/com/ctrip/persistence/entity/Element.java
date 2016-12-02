package com.ctrip.persistence.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ctrip.persistence.enums.MLFlowStatus;

/**
 * @author jtzhang
 */
@Entity
@Table(name = "t_element")
public class Element extends AbstractElement {
	private String latestRuntimeId;
	private MLFlowStatus status = MLFlowStatus.STOP;
	private List<ElementParam> params;
	@Transient
	public List<ElementParam> getParams() {
		return params;
	}

	public Element setParams(List<ElementParam> params) {
		this.params = params;
		return this;
	}

    @Enumerated(EnumType.STRING)
    public MLFlowStatus getStatus() {
        return status;
    }

    public AbstractElement setStatus(MLFlowStatus status) {
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
