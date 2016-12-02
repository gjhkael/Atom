package com.ctrip.persistence.service;

import com.ctrip.persistence.entity.*;
import com.ctrip.persistence.enums.MLFlowStatus;
import com.ctrip.persistence.pojo.DataResult;
import com.ctrip.persistence.pojo.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by juntao on 2/14/16.
 * 算法开发 service
 *
 * @author 张峻滔
 */
public interface MLFlowHistoryService {
	void saveMLFlowHistory(MLFlowHistory mLFlowHistory);
	
	void saveModelHistory(MLFlowHistory mLFlowHistory, Long modelId);
	
	MLFlowHistory getById(Long id);
	
	MLFlowHistory getByRuntimeId(String runtimeId);
	
	void save(MLFlowHistory mLFlowHistory);
}
