package com.subcrii.subcrii.domain.creator.service;

import com.subcrii.subcrii.domain.creator.dto.CreatorResponse;
import com.subcrii.subcrii.domain.creator.entity.Creator;
import com.subcrii.subcrii.domain.creator.repository.CreatorRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatorService {
    private final CreatorRepository creatorRepository;

    public List<CreatorResponse> getCreatorsByFavorites() {
        PageRequest pageRequest = PageRequest.of(0, 4);
        List<Creator> creatorList = creatorRepository.findCreatorsOrderByFavoritesAsc(pageRequest);

        return CreatorResponse.toDto(creatorList);
    }

    public List<CreatorResponse> getCreatorsBySubscription() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Creator> creatorList = creatorRepository.findCreatorsOrderBySubscriptionAsc(pageRequest);

        return CreatorResponse.toDto(creatorList);
    }

    public List<CreatorResponse> getCreatorsByCategory(int page, int size, UUID category_id) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Creator> creatorList = creatorRepository.findCreatorsByCategory_Id(category_id, pageRequest);

        return CreatorResponse.toDto(creatorList);
    }

    public List<CreatorResponse> getCreatorsOrderByCreatedAt(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Creator> creatorList = creatorRepository.findCreatorOrderByCreatedAt(pageRequest);

        return CreatorResponse.toDto(creatorList);
    }
}
