package com.subcrii.subcrii.domain.creator.repository;

import com.subcrii.subcrii.domain.creator.entity.Creator;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, UUID> {

    @Query("""
        select c
        from Favorites f
        right join f.creator c
        group by c
        order by count(f) desc
        """)
    List<Creator> findCreatorsOrderByFavoritesAsc(Pageable pageable);


    @Query("""
        select c
        from Subscription s
        right join s.creator c
        group by c
        order by count(s) desc
        """)
    List<Creator> findCreatorsOrderBySubscriptionAsc(Pageable pageable);

    @Query("""
        select c
        from Creator c
        where c.category.id = :c_id
        order by c.createdAt desc
        """)
    List<Creator> findCreatorsByCategory_Id(@Param(value = "c_id") UUID id, Pageable pageable);


    @Query("""
        select c
        from Creator c
        order by c.createdAt desc
        """)
    List<Creator> findCreatorOrderByCreatedAt(Pageable pageable);
}
