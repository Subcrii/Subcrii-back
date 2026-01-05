package com.subcrii.subcrii.domain.creator.service;

import com.subcrii.subcrii.domain.creator.dto.CreatorDetailResponse;
import com.subcrii.subcrii.domain.creator.dto.CreatorResponse;
import com.subcrii.subcrii.domain.creator.entity.Category;
import com.subcrii.subcrii.domain.creator.entity.Creator;
import com.subcrii.subcrii.domain.creator.repository.ContentRepository;
import com.subcrii.subcrii.domain.creator.repository.CreatorRepository;
import com.subcrii.subcrii.domain.subscription.entity.SubscriptionStatus;
import com.subcrii.subcrii.domain.subscription.repository.SubscriptionRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatorService {
    private final CreatorRepository creatorRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ContentRepository contentRepository;

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

    @Transactional(readOnly = true)
    public CreatorDetailResponse getCreatorDetail(UUID creatorId) {
        Creator creator = creatorRepository.findDetailById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("Creator not found: " + creatorId));

        long subscriberCount = subscriptionRepository.countByCreator_IdAndSubscriptionStatus(
                creatorId, SubscriptionStatus.ACTIVE
        );

        long postCount = contentRepository.countByCreatorId(creatorId);

        return toResponse(creator, subscriberCount, postCount);
    }

    private CreatorDetailResponse toResponse(Creator creator, long subscriberCount, long postCount) {
        Category category = creator.getCategory();

        CreatorDetailResponse.CategoryResponse categoryResponse = (category == null)
                ? null
                : new CreatorDetailResponse.CategoryResponse(
                        category.getId(),
                        category.getParentId(),
                        category.getName()
                );

        return new CreatorDetailResponse(
                creator.getId(),
                creator.getNickName(),
                creator.getInformation(),
                creator.getSubscriptionPrice(),
                categoryResponse,
                subscriberCount,
                postCount,
                creator.getCreatedAt(),
                creator.getUpdatedAt()
        );
    }
}
