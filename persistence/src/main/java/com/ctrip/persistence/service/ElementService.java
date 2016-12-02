package com.ctrip.persistence.service;

import java.util.List;

import com.ctrip.persistence.entity.Element;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface ElementService {
	 List<Long> getIdByMlFlowId(Long mlFlowId);
	 
	 Long getElementTplIdById(Long id);
	 
	 Element getById(Long id);
	 
	 List<Element> getElementByMlFlowId(Long mlFlowId);
}
