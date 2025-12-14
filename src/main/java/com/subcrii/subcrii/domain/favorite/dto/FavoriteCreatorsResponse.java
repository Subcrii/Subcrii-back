package com.subcrii.subcrii.domain.favorite.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FavoriteCreatorsResponse (
    UUID id,
    String nickName,
    String category,
    LocalDateTime createdAt
){}
