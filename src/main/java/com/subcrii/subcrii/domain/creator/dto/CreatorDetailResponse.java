package com.subcrii.subcrii.domain.creator.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatorDetailResponse(
        UUID id,
        String nickName,
        String information,
        Integer subscriptionPrice,
        CategoryResponse category,
        long subscriberCount,
        long postCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record CategoryResponse(
            UUID id,
            UUID parentId,
            String name
    ) {}
}
