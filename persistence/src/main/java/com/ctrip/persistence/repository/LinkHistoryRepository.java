package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.Link;
import com.ctrip.persistence.entity.LinkHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author juntao zhang
 */
public interface LinkHistoryRepository
    extends JpaRepository<LinkHistory, Long>, JpaSpecificationExecutor<LinkHistory> {
    List<Long> findIdByMlFlowId(Long id);

    List<LinkHistory> findByMlFlowId(Long id);
    
    @Query("select sourceId from LinkHistory l where l.mlFlowId = :mlFlowId and l.targetId = :targetId")
    List<Long> getSourceIdByMlFlowIdAndTargetId(@Param("mlFlowId") Long mlFlowId, @Param("targetId") Long targetId);
}
