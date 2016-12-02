package com.ctrip.persistence.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.Element;
import com.ctrip.persistence.repository.ElementRepository;
import com.ctrip.persistence.repository.ElementTplRepository;
import com.ctrip.persistence.service.ElementService;
import com.ctrip.persistence.service.ElementTplService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementServiceImpl implements ElementService {

    @Resource
    private ElementRepository elementRepository;

	@Override
	public List<Long> getIdByMlFlowId(Long mlFlowId) {
		// TODO Auto-generated method stub
		return elementRepository.getIdByMlFlowId(mlFlowId);
	}

	@Override
	public Long getElementTplIdById(Long id) {
		// TODO Auto-generated method stub
		return elementRepository.getElementTplIdById(id);
	}

	@Override
	public Element getById(Long id) {
		// TODO Auto-generated method stub
		return elementRepository.findOne(id);
	}

	@Override
	public List<Element> getElementByMlFlowId(Long mlFlowId) {
		// TODO Auto-generated method stub
		return elementRepository.findByMlFlowId(mlFlowId);
	}

    
}
