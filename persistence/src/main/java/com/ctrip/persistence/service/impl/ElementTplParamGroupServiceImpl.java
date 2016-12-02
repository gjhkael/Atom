package com.ctrip.persistence.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.repository.ElementTplParamGroupRepository;
import com.ctrip.persistence.service.ElementTplParamGroupService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementTplParamGroupServiceImpl implements ElementTplParamGroupService {

    @Resource
    private ElementTplParamGroupRepository elementTplParamGroupRepository;

	@Override
	public String getNameById(Long id) {
		// TODO Auto-generated method stub
		return elementTplParamGroupRepository.getNameById(id);
	}



    
}
