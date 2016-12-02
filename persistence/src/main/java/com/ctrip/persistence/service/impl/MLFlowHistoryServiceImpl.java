package com.ctrip.persistence.service.impl;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctrip.persistence.entity.AbstractElement;
import com.ctrip.persistence.entity.Element;
import com.ctrip.persistence.entity.ElementHistory;
import com.ctrip.persistence.entity.ElementParamHistory;
import com.ctrip.persistence.entity.LinkHistory;
import com.ctrip.persistence.entity.MLFlow;
import com.ctrip.persistence.entity.MLFlowHistory;
import com.ctrip.persistence.enums.MLFlowStatus;
import com.ctrip.persistence.repository.ElementHistoryRepository;
import com.ctrip.persistence.repository.ElementParamHistoryRepository;
import com.ctrip.persistence.repository.ElementRepository;
import com.ctrip.persistence.repository.LinkHistoryRepository;
import com.ctrip.persistence.repository.MLFlowHistoryRepository;
import com.ctrip.persistence.repository.MLFlowRepository;
import com.ctrip.persistence.service.MLFlowHistoryService;
import com.google.common.collect.Maps;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class MLFlowHistoryServiceImpl implements MLFlowHistoryService {

    @Resource
    private MLFlowHistoryRepository mLFlowHistoryRepository;
    
    @Resource
    private MLFlowRepository mLFlowRepository;
    
    @Resource
    private ElementHistoryRepository elementHistoryRepository;
    
    @Resource
    private ElementRepository elementRepository;
    
    @Resource
    private ElementParamHistoryRepository elementParamHistoryRepository;
    
    @Resource
    private LinkHistoryRepository linkHistoryRepository;

    @Transactional
	@Override
	public void saveMLFlowHistory(MLFlowHistory mLFlowHistory) {
    	Long mlFlowId = mLFlowHistory.getId();
    	mLFlowHistory.setId(null);
        Map<String, AbstractElement> uuidEleMap = Maps.newHashMap();
        Long startTime = System.currentTimeMillis();
        mLFlowHistory.setMlFlowId(mlFlowId);
        String flowuuid = UUID.randomUUID().toString();
        mLFlowHistory.setRuntimeId(flowuuid);
        mLFlowHistoryRepository.save(mLFlowHistory);
        MLFlow mlFlow = mLFlowRepository.findOne(mlFlowId);
        mlFlow.setLatestRuntimeId(flowuuid);
        mLFlowRepository.save(mlFlow);
        Long historyId = mLFlowHistory.getId();
//        MLFlowHistory entity = mLFlowHistoryRepository.findOne(historyId);
//        entity.from(mLFlowHistory);
//        entity.setCronId(historyId);
//        mLFlowHistoryRepository.save(entity);
        for (ElementHistory elementhistory : mLFlowHistory.getElements()) {
            // associate with MLFlow
        	Long modelId = elementhistory.getId();
        	elementhistory.setMlFlowId(historyId);
        	elementhistory.setId(null);
            String elementUuid = UUID.randomUUID().toString();
           // elementhistory.setCronId(elementUuid);
            elementhistory.setRuntimeId(elementUuid);
            elementhistory.setElementId(modelId);
            elementHistoryRepository.save(elementhistory); 
            Element element = elementRepository.findOne(modelId);
			element.setLatestRuntimeId(elementUuid);
			elementRepository.save(element);
            for (ElementParamHistory param : elementhistory.getParams()) {
                param.setMlFlowId(historyId);
                param.setElementId(elementhistory.getId());
                elementParamHistoryRepository.save(param);
            }
            uuidEleMap.put(elementhistory.getUuid(), elementhistory);
        }
        for (LinkHistory link : mLFlowHistory.getLinks()) {
            // associate with MLFlow
            link.setMlFlowId(historyId);
            link.setSourceId(uuidEleMap.get(link.getSourceUuid()).getId());
            link.setTargetId(uuidEleMap.get(link.getTargetUuid()).getId());
            linkHistoryRepository.save(link);
        }
	}

	@Override
	public MLFlowHistory getById(Long id) {
		// TODO Auto-generated method stub
		return mLFlowHistoryRepository.findOne(id);
	}

	@Override
	public void save(MLFlowHistory mLFlowHistory) {
		// TODO Auto-generated method stub
		mLFlowHistoryRepository.save(mLFlowHistory);
	}

	@Override
	public MLFlowHistory getByRuntimeId(String runtimeId) {
		// TODO Auto-generated method stub
		return mLFlowHistoryRepository.findByRuntimeId(runtimeId);
	}

	@Override
	public void saveModelHistory(MLFlowHistory mLFlowHistory, Long modelId) {
		// TODO Auto-generated method stub
		Long mlFlowId = mLFlowHistory.getId();
		
    	mLFlowHistory.setId(null);
        Long startTime = System.currentTimeMillis();
        mLFlowHistory.setMlFlowId(mlFlowId);
        String flowuuid = UUID.randomUUID().toString();
        mLFlowHistory.setRuntimeId(flowuuid);
        mLFlowHistoryRepository.save(mLFlowHistory);
        MLFlow mlFlow = mLFlowRepository.findOne(mlFlowId);
        mlFlow.setLatestRuntimeId(flowuuid);
        mLFlowRepository.save(mlFlow);
        Long historyId = mLFlowHistory.getId();
        // associate with MLFlow
        for (ElementHistory elementhistory : mLFlowHistory.getElements()){
        	if(elementhistory.getId().equals(modelId)){
        		elementhistory.setMlFlowId(historyId);
        		elementhistory.setId(null);
				String elementUuid = UUID.randomUUID().toString();
			//	elementhistory.setCronId(elementUuid);
				elementhistory.setRuntimeId(elementUuid);
				elementhistory.setElementId(modelId);
				elementHistoryRepository.save(elementhistory);
				Element element = elementRepository.findOne(modelId);
				element.setLatestRuntimeId(elementUuid);
				elementRepository.save(element);
				for (ElementParamHistory param : elementhistory.getParams()) {
					param.setMlFlowId(historyId);
					param.setElementId(elementhistory.getId());
					elementParamHistoryRepository.save(param);
				}
        	}
        }
        
        
	}

    
}
