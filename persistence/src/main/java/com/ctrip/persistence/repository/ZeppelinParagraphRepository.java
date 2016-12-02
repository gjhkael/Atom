package com.ctrip.persistence.repository;

import com.ctrip.persistence.entity.ZeppelinParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author 张峻滔
 */
public interface ZeppelinParagraphRepository
    extends JpaRepository<ZeppelinParagraph, Long>, JpaSpecificationExecutor<ZeppelinParagraph> {
    ZeppelinParagraph findByUuid(String uuid);

    ZeppelinParagraph findByElementId(Long elementId);

    List<ZeppelinParagraph> findByStatus(String status);
}
