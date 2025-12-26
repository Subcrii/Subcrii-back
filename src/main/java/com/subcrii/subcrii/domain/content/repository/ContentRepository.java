package com.subcrii.subcrii.domain.content.repository;

import com.subcrii.subcrii.domain.content.dto.ContentDetailResponse;
import com.subcrii.subcrii.domain.content.dto.ContentListResponse;
import com.subcrii.subcrii.domain.content.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
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
                where c.creator.id = :creatorId
                order by c.createdAt desc
            """, countQuery = """
              select count(c)
              from Content c
              where c.creator.id = :creatorId
            """)
    Page<ContentListResponse> findContentListByCreatorId(@Param("creatorId") UUID creatorId, Pageable pageable);

    @Query("""
            select new com.subcrii.subcrii.domain.content.dto.ContentDetailResponse(
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
            where c.id = :contentId
            and c.creator.id = :creatorId
            """)
    Optional<ContentDetailResponse> findDetailByCreatorIdAndContentId(@Param("creatorId") UUID creatorId, @Param("contentId") UUID contentId);
}
