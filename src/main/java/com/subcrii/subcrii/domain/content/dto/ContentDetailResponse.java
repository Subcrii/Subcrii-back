package com.subcrii.subcrii.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ContentDetailResponse {
    private UUID id;
    private UUID creatorId;
    private String creatorNickName;
    private String categoryName;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
