package com.ctrip.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ctrip.persistence.entity.Link;

/**
 * @author juntao zhang
 */
public interface LinkRepository
    extends JpaRepository<Link, Long>, JpaSpecificationExecutor<Link> {
    List<Long> findIdByMlFlowId(Long id);

    List<Link> findByMlFlowId(Long id);
    
    @Query("select sourceId from Link l where l.mlFlowId = :mlFlowId and l.targetId = :targetId")
    List<Long> getSourceIdByMlFlowIdAndTargetId(@Param("mlFlowId") Long mlFlowId, @Param("targetId") Long targetId);

}
