package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ElementParam;
import com.ctrip.persistence.entity.ElementParamHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author juntao zhang
 */
public interface ElementParamHistoryRepository
    extends JpaRepository<ElementParamHistory, Long>, JpaSpecificationExecutor<ElementParamHistory> {
    List<Long> findIdByMlFlowId(Long id);

    List<ElementParamHistory> findByMlFlowId(Long id);
    
    @Query("select id from ElementParamHistory where mlFlowId = :mlFlowId and elementId = :elementId")
    List<Long> getIdByMlFlowIdAndElementId(@Param("mlFlowId") Long mlFlowId, @Param("elementId") Long elementId);
    
    @Query("select value from ElementParamHistory where id = :id")
    String getValueById(@Param("id") Long id);
    
    @Query("select elementTplParamId from ElementParamHistory where id = :id")
    Long getElementTplParamIdById(@Param("id") Long id);
}
