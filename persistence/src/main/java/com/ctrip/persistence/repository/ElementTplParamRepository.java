package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ElementTplParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author juntao zhang
 */
public interface ElementTplParamRepository
    extends JpaRepository<ElementTplParam, Long>, JpaSpecificationExecutor<ElementTplParam> {
	@Query("select groupId from ElementTplParam where id = :id")
	Long getGroupIdById(@Param("id") Long id);
	
	@Query("select name from ElementTplParam where id = :id")
	String getNameById(@Param("id") Long id);
}
