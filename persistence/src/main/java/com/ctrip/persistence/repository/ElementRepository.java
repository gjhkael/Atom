package com.ctrip.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ctrip.persistence.entity.Element;

/**
 * @author juntao zhang
 */
public interface ElementRepository
    extends JpaRepository<Element, Long>, JpaSpecificationExecutor<Element> {
    List<Long> findIdByMlFlowId(Long id);

    List<Element> findByMlFlowId(Long id);
    
    @Query("select id from Element where mlFlowId = :mlFlowId")
    List<Long> getIdByMlFlowId(@Param("mlFlowId") Long mlFlowId);
    
    @Query("select elementTplId from Element where id = :id")
    Long getElementTplIdById(@Param("id") Long id);
}
