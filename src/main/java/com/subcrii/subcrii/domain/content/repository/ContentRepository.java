package com.subcrii.subcrii.domain.content.repository;

import com.subcrii.subcrii.domain.content.dto.ContentListResponse;
import com.subcrii.subcrii.domain.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {
    @Query(value = """
                select new com.subcrii.subcrii.domain.content.dto.ContentListResponse(
                    c.id,
                    cr.id,
                    cr.nickName,
                    cat.name,
                    c.title,
                    c.description,
                    c.createdAt
                )
                from Content c
                join c.creator cr
                join cr.category cat
                where cr.id = :creatorId
                order by c.createdAt desc
            """, countQuery = """
              select count(c)
              from Content c
              where c.creator.id = :creatorId
            """)
    Page<ContentListResponse> findContentListByCreatorId(UUID creatorId, Pageable pageable);
}
