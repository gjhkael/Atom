package com.ctrip.persistence.repository;

import java.util.List;

import com.ctrip.persistence.entity.ElementTpl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author juntao zhang
 */
public interface ElementTplRepository
    extends JpaRepository<ElementTpl, Long>, JpaSpecificationExecutor<ElementTpl> {
	@Query("select type from ElementTpl where id = :id")
	String getTypeById(@Param("id") Long id);
	
	@Query("select text from ElementTpl where id = :id")
	String getTextById(@Param("id") Long id);
	
	List<ElementTpl> findByOwnerId(Long ownerId);
}
