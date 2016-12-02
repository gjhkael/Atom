package com.ctrip.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ctrip.persistence.entity.MLFlow;

/**
 * @author juntao zhang
 */
public interface MLFlowRepository
    extends JpaRepository<MLFlow, Long>, JpaSpecificationExecutor<MLFlow> {
	
	@Query("select name from MLFlow where id = :id")
	String getNameById(@Param("id") Long id);
	
	@Query("select ownerId from MLFlow where id = :id")
	Long getOwnerIdByMlFlowId(@Param("id") Long id);
	
	MLFlow findByName(String name);
	
	List<MLFlow> findByOwnerId(Long ownerId);
}
