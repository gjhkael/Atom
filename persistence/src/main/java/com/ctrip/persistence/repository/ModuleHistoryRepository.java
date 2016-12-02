package com.ctrip.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ctrip.persistence.entity.Element;
import com.ctrip.persistence.entity.ModuleHistory;
import com.ctrip.persistence.entity.ModuleHistoryPK;

/**
 * @author juntao zhang
 */
public interface ModuleHistoryRepository
    extends JpaRepository<ModuleHistory, ModuleHistoryPK>, JpaSpecificationExecutor<ModuleHistory> {
	
	//@Query("select logDir from TModuleHistoryEntity where uuid = :uuid and scheduleTimes = :scheduleTimes")
	ModuleHistory findByUuidAndScheduleTimes(@Param("uuid") String uuid, @Param("scheduleTimes")  int scheduleTimes);
//	ModuleHistory findByStatus(String status);
}
