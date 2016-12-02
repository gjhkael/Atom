package com.ctrip.persistence.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.ElementParam;
import com.ctrip.persistence.repository.ElementParamRepository;
import com.ctrip.persistence.service.ElementParamService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementParamServiceImpl implements ElementParamService {

    @Resource
    private ElementParamRepository elementParamRepository;

	@Override
	public List<Long> findIdByMlFlowId(Long id) {
		// TODO Auto-generated method stub
		return elementParamRepository.findIdByMlFlowId(id);
	}

	@Override
	public List<ElementParam> getByMlFlowId(Long id) {
		// TODO Auto-generated method stub
		return elementParamRepository.findByMlFlowId(id);
	}

	@Override
	public List<Long> getIdByMlFlowIdAndElementId(Long mlFlowId, Long elementId) {
		// TODO Auto-generated method stub
		return elementParamRepository.getIdByMlFlowIdAndElementId(mlFlowId, elementId);
	}

	@Override
	public Long getElementTplParamIdById(Long id) {
		// TODO Auto-generated method stub
		return elementParamRepository.getElementTplParamIdById(id);
	}

	@Override
	public String getValueById(Long id) {
		// TODO Auto-generated method stub
		return elementParamRepository.getValueById(id);
	}

    
}
