package com.ctrip.persistence.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.repository.ElementTplParamRepository;
import com.ctrip.persistence.service.ElementTplParamService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementTplParamServiceImpl implements ElementTplParamService {

    @Resource
    private ElementTplParamRepository elementTplParamRepository;

	@Override
	public Long getGroupIdById(Long id) {
		// TODO Auto-generated method stub
		return elementTplParamRepository.getGroupIdById(id);
	}

	@Override
	public String getNameById(Long id) {
		// TODO Auto-generated method stub
		return elementTplParamRepository.getNameById(id);
	}


    
}
