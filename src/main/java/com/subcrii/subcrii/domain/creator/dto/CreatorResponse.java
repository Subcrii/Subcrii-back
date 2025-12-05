package com.subcrii.subcrii.domain.creator.dto;

import com.subcrii.subcrii.domain.creator.entity.Creator;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatorResponse {

    private UUID id;
    private String nickName;
    private String information;
    private Integer subscriptionPrice;
    private String category;


    public static List<CreatorResponse> toDto(List<Creator> creatorList) {

        return creatorList.stream()
                .map(creator -> {
                    CreatorResponse dto = CreatorResponse.builder()
                            .id(creator.getId())
                            .nickName(creator.getNickName())
                            .information(creator.getInformation())
                            .subscriptionPrice(creator.getSubscriptionPrice())
                            .category(creator.getCategory().getId().toString())
                            .build();
                    return dto;
                })
                .toList();
    }

}
