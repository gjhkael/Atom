package com.ctrip.persistence.service;

import com.ctrip.persistence.entity.ModuleHistory;
import com.ctrip.persistence.entity.ModuleHistoryPK;


/**
 * Created by juntao on 2/14/16.
 * 算法开发 service
 *
 * @author 张峻滔
 */
public interface ModuleHistoryService {
	ModuleHistory findOne(ModuleHistoryPK key);
}
