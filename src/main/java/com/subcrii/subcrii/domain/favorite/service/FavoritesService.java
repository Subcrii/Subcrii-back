package com.subcrii.subcrii.domain.favorite.service;

import com.subcrii.subcrii.domain.favorite.dto.FavoriteCreatorsResponse;
import com.subcrii.subcrii.domain.favorite.entity.Favorites;
import com.subcrii.subcrii.domain.favorite.repository.FavoritesRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;

    @Transactional
    public void addFavorites(UUID memberId, UUID creatorId) {
        if (favoritesRepository.existsByMemberIdAndCreatorId(memberId, creatorId)){
            return;
        }
        Favorites favorite = Favorites.create(memberId, creatorId);

        favoritesRepository.save(favorite);
    }

    @Transactional
    public void removeFavorites(UUID memberId, UUID creatorId) {
        favoritesRepository.deleteByMemberIdAndCreatorId(memberId, creatorId);
    }

    public Page<FavoriteCreatorsResponse> findFavoriteCreatorsByMemberId(UUID memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return favoritesRepository.findFavoriteCreatorsByMemberId(memberId, pageable);
    }

}
