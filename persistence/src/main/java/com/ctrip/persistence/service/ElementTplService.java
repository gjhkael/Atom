package com.ctrip.persistence.service;

import com.ctrip.persistence.entity.ElementTpl;
import com.ctrip.persistence.pojo.Result;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface ElementTplService {
	String getTypeById(Long id);
	
	String getTextById(Long id);
	
	ElementTpl deleteElementTpl(Long id);
	
	ElementTpl findOne(Long id);
}
