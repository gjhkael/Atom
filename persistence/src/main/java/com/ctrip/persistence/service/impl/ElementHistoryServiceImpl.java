package com.ctrip.persistence.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.ElementHistory;
import com.ctrip.persistence.entity.MLFlowHistory;
import com.ctrip.persistence.repository.ElementHistoryRepository;
import com.ctrip.persistence.service.ElementHistoryService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ElementHistoryServiceImpl implements ElementHistoryService {

    @Resource
    private ElementHistoryRepository elementHistoryRepository;

	@Override
	public void saveOrUpdateElementHistory(ElementHistory elementHistory) {
		// TODO Auto-generated method stub
		elementHistoryRepository.save(elementHistory);
	}

	@Override
	public List<Long> getIdByMlFlowHistoryId(Long mlFlowHistoryId) {
		// TODO Auto-generated method stub
		return elementHistoryRepository.getIdByMlFlowId(mlFlowHistoryId);
	}

	@Override
	public Long getElementTplIdById(Long id) {
		// TODO Auto-generated method stub
		return elementHistoryRepository.getElementTplIdById(id);
	}

	@Override
	public ElementHistory getById(Long id) {
		// TODO Auto-generated method stub
		return elementHistoryRepository.findOne(id);
	}

	@Override
	public ElementHistory getByRuntimeId(String runtimeId) {
		// TODO Auto-generated method stub
		return elementHistoryRepository.findByRuntimeId(runtimeId);
	}

	@Override
	public void save(ElementHistory elementHistory) {
		// TODO Auto-generated method stub
		elementHistoryRepository.save(elementHistory);
	}

    
}
