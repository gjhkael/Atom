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
import com.ctrip.persistence.entity.ModuleHistory;
import com.ctrip.persistence.entity.ModuleHistoryPK;
import com.ctrip.persistence.enums.MLFlowStatus;
import com.ctrip.persistence.repository.ElementHistoryRepository;
import com.ctrip.persistence.repository.ElementParamHistoryRepository;
import com.ctrip.persistence.repository.ElementRepository;
import com.ctrip.persistence.repository.LinkHistoryRepository;
import com.ctrip.persistence.repository.MLFlowHistoryRepository;
import com.ctrip.persistence.repository.MLFlowRepository;
import com.ctrip.persistence.repository.ModuleHistoryRepository;
import com.ctrip.persistence.service.MLFlowHistoryService;
import com.ctrip.persistence.service.ModuleHistoryService;
import com.google.common.collect.Maps;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class ModuleHistoryServiceImpl implements ModuleHistoryService {

	@Resource
    private ModuleHistoryRepository moduleHistoryRepository;
	
	@Override
	public ModuleHistory findOne(ModuleHistoryPK key) {
		// TODO Auto-generated method stub
		return moduleHistoryRepository.findOne(key);
	}

}
