package com.ctrip.persistence.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.LinkHistory;
import com.ctrip.persistence.repository.LinkHistoryRepository;
import com.ctrip.persistence.service.LinkHistoryService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class LinkHistoryServiceImpl implements LinkHistoryService {

    @Resource
    private LinkHistoryRepository linkHistoryRepository;

	@Override
	public void saveOrUpdatelinkHistory(LinkHistory linkHistory) {
		// TODO Auto-generated method stub
		linkHistoryRepository.save(linkHistory);
	}

	@Override
	public List<Long> getSourceIdByMlFlowHistoryIdAndTargetId(
			Long mlFlowHistoryId, Long targetId) {
		// TODO Auto-generated method stub
		return linkHistoryRepository.getSourceIdByMlFlowIdAndTargetId(mlFlowHistoryId, targetId);
	}

	


    
}
