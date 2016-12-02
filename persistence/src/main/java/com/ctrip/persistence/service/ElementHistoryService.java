package com.ctrip.persistence.service;

import java.util.List;

import com.ctrip.persistence.entity.ElementHistory;
import com.ctrip.persistence.entity.MLFlowHistory;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface ElementHistoryService {
	void saveOrUpdateElementHistory(ElementHistory elementHistory);
	List<Long> getIdByMlFlowHistoryId(Long mlFlowHistoryId);
	Long getElementTplIdById(Long id);
	ElementHistory getById(Long id);
	ElementHistory getByRuntimeId(String runtimeId);
	void save(ElementHistory elementHistory);
}
