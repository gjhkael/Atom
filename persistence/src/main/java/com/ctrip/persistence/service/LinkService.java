package com.ctrip.persistence.service;

import java.util.List;

import com.ctrip.persistence.entity.Link;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface LinkService {
	 List<Long> getSourceIdByMlFlowIdAndTargetId(Long mlFlowId, Long targetId);
	 
	 List<Link> getByMlFlowId(Long id);
}
