package com.ctrip.persistence.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ctrip.persistence.entity.Link;
import com.ctrip.persistence.repository.LinkRepository;
import com.ctrip.persistence.service.LinkService;

/**
 * Created by juntao on 2/3/16.
 *
 * @author jtzhang@ctrip.com
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Resource
    private LinkRepository linkRepository;

	@Override
	public List<Long> getSourceIdByMlFlowIdAndTargetId(Long mlFlowId,
			Long targetId) {
		// TODO Auto-generated method stub
		return linkRepository.getSourceIdByMlFlowIdAndTargetId(mlFlowId, targetId);
	}

	@Override
	public List<Link> getByMlFlowId(Long id) {
		// TODO Auto-generated method stub
		return linkRepository.findByMlFlowId(id);
	}


    
}
