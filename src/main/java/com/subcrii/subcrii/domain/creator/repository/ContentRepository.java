package com.subcrii.subcrii.domain.creator.repository;

import com.subcrii.subcrii.domain.creator.entity.Content;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, UUID> {

    long countByCreatorId(UUID creatorId);
}
