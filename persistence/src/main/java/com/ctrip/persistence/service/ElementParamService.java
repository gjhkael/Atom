package com.ctrip.persistence.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.ctrip.persistence.entity.ElementParam;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface ElementParamService {
	List<ElementParam> getByMlFlowId(Long id);
	
	List<Long> findIdByMlFlowId(Long id);
	
	List<Long> getIdByMlFlowIdAndElementId(Long mlFlowId, Long elementId);
	
	Long getElementTplParamIdById(Long id);
	
	String getValueById(Long id);
}
