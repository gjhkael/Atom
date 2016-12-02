package com.ctrip.persistence.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.ctrip.persistence.entity.ElementParam;



/**
 * 用户信息服务层
 *
 * @author 张峻滔
 */
public interface ElementTplParamService {
	Long getGroupIdById(Long id);
	
	String getNameById(Long id);
}
