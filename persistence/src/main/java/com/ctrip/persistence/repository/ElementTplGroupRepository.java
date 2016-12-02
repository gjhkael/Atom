package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ElementTplGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author juntao zhang
 */
public interface ElementTplGroupRepository
    extends JpaRepository<ElementTplGroup, Long>, JpaSpecificationExecutor<ElementTplGroup> {
    ElementTplGroup findByName(String custom);
}
