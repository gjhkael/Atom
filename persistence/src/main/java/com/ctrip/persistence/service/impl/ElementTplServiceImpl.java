package com.ctrip.persistence.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.ElementTpl;
import com.ctrip.persistence.pojo.Result;
import com.ctrip.persistence.repository.ElementTplRepository;
import com.ctrip.persistence.service.ElementTplService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementTplServiceImpl implements ElementTplService {

    @Resource
    private ElementTplRepository elementTplRepository;

	@Override
	public String getTypeById(Long id) {
		// TODO Auto-generated method stub
		return elementTplRepository.getTypeById(id);
	}

	@Override
	public String getTextById(Long id) {
		// TODO Auto-generated method stub
		return elementTplRepository.getTextById(id);
	}

	@Override
	public ElementTpl deleteElementTpl(Long id) {
		// TODO Auto-generated method stub
		ElementTpl elementTpl = elementTplRepository.findOne(id);
		elementTplRepository.delete(id);
		return elementTpl;
	}

	@Override
	public ElementTpl findOne(Long id) {
		// TODO Auto-generated method stub
		return elementTplRepository.findOne(id);
	}
    
}
