package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ElementTplParamGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author juntao zhang
 */
public interface ElementTplParamGroupRepository
    extends JpaRepository<ElementTplParamGroup, Long>, JpaSpecificationExecutor<ElementTplParamGroup> {
    ElementTplParamGroup findByName(String other);
    
    @Query("select name from ElementTplParamGroup where id = :id")
    String getNameById(@Param("id") Long id);
}
