package com.ctrip.persistence.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.ElementParamHistory;
import com.ctrip.persistence.repository.ElementParamHistoryRepository;
import com.ctrip.persistence.service.ElementParamHistoryService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementParamHistoryServiceImpl implements ElementParamHistoryService {

    @Resource
    private ElementParamHistoryRepository elementParamHistoryRepository;

	@Override
	public void saveOrUpdateElementParamHistory(
			ElementParamHistory elementParamHistory) {
		// TODO Auto-generated method stub
		elementParamHistoryRepository.save(elementParamHistory);
	}

	@Override
	public List<Long> getIdByMlFlowIdAndElementId(Long mlFlowId,
			Long elementId) {
		// TODO Auto-generated method stub
		return elementParamHistoryRepository.getIdByMlFlowIdAndElementId(mlFlowId, elementId);
	}

	@Override
	public String getValueById(Long id) {
		// TODO Auto-generated method stub
		return elementParamHistoryRepository.getValueById(id);
	}

	@Override
	public Long getElementTplParamIdById(Long id) {
		// TODO Auto-generated method stub
		return elementParamHistoryRepository.getElementTplParamIdById(id);
	}

	
    
}
