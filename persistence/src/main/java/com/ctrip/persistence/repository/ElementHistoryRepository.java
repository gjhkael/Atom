package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.Element;
import com.ctrip.persistence.entity.ElementHistory;
import com.ctrip.persistence.entity.MLFlowHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author juntao zhang
 */
public interface ElementHistoryRepository
    extends JpaRepository<ElementHistory, Long>, JpaSpecificationExecutor<ElementHistory> {
    List<Long> findIdByMlFlowId(Long id);

    List<ElementHistory> findByMlFlowId(Long id);
    
    @Query("select id from ElementHistory where mlFlowId = :mlFlowId")
    List<Long> getIdByMlFlowId(@Param("mlFlowId") Long mlFlowId);
    
    @Query("select elementTplId from ElementHistory where id = :id")
    Long getElementTplIdById(@Param("id") Long id);
    
  //  ElementHistory findByMlFlowIdAndStartTime(Long id, Long time);
    
    ElementHistory findByRuntimeId(String runtimeId);
    
}
