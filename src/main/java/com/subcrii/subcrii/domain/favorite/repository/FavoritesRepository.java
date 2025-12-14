package com.subcrii.subcrii.domain.favorite.repository;

import com.subcrii.subcrii.domain.favorite.dto.FavoriteCreatorsResponse;
import com.subcrii.subcrii.domain.favorite.entity.Favorites;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, UUID> {

    void deleteByMemberIdAndCreatorId(UUID memberId, UUID creatorId);

    boolean existsByMemberIdAndCreatorId(UUID memberId, UUID creatorId);


    @Query(
            value = """
    select new com.subcrii.subcrii.domain.favorite.dto.FavoriteCreatorsResponse(
      c.id,
      c.nickName,
      c.category.name,
      f.createdAt
    )
    from Favorites f, Creator c
    where f.memberId = :memberId
      and c.id = f.creatorId
    order by f.createdAt desc, f.id desc
  """,
            countQuery = """
    select count(f)
    from Favorites f, Creator c
    where f.memberId = :memberId
      and c.id = f.creatorId
  """
    )
    Page<FavoriteCreatorsResponse> findFavoriteCreatorsByMemberId(@Param("memberId")UUID memberId, Pageable pageable);
}
