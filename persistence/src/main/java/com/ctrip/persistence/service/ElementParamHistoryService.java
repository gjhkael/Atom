package com.ctrip.persistence.service;

import java.util.List;

import com.ctrip.persistence.entity.ElementParamHistory;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface ElementParamHistoryService {
	void saveOrUpdateElementParamHistory(ElementParamHistory elementParamHistory);
	List<Long> getIdByMlFlowIdAndElementId(Long mlFlowId, Long elementId);
	String getValueById(Long id);
	Long getElementTplParamIdById(Long id);
}
