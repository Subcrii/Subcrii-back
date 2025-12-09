package com.subcrii.subcrii.domain.favorite.repository;

import com.subcrii.subcrii.domain.favorite.entity.Favorites;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, UUID> {

    void deleteByMemberIdAndCreatorId(UUID memberId, UUID creatorId);

    boolean existsByMemberIdAndCreatorId(UUID memberId, UUID creatorId);
}
