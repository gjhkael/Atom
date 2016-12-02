package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ElementParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author juntao zhang
 */
public interface ElementParamRepository
    extends JpaRepository<ElementParam, Long>, JpaSpecificationExecutor<ElementParam> {
    List<Long> findIdByMlFlowId(Long id);

    List<ElementParam> findByMlFlowId(Long id);
    
    @Query("select id from ElementParam where mlFlowId = :mlFlowId and elementId = :elementId")
    List<Long> getIdByMlFlowIdAndElementId(@Param("mlFlowId") Long mlFlowId, @Param("elementId") Long elementId);
    
    @Query("select elementTplParamId from ElementParam where id = :id")
    Long getElementTplParamIdById(@Param("id") Long id);
    
    @Query("select value from ElementParam where id = :id")
    String getValueById(@Param("id") Long id);
}
