package com.ctrip.persistence.service;

import java.util.List;

import com.ctrip.persistence.entity.LinkHistory;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface LinkHistoryService {
	void saveOrUpdatelinkHistory(LinkHistory linkHistory);
	
	List<Long> getSourceIdByMlFlowHistoryIdAndTargetId(Long mlFlowHistoryId, Long targetId);
}
