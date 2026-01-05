package com.subcrii.subcrii.domain.creator.controller;

import com.subcrii.subcrii.domain.creator.dto.CreatorDetailResponse;
import com.subcrii.subcrii.domain.creator.dto.CreatorResponse;
import com.subcrii.subcrii.domain.creator.service.CreatorService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creators")
@RequiredArgsConstructor
public class CreatorController {
    private final CreatorService creatorService;

    @GetMapping("/main/favorites")
    @Operation(summary = "크리에이터 스크랩 상위 4명 목록 조회", description = "크리에이터를 스크랩 많은 순으로 상위 4명의 목록을 반환합니다.")
    public ResponseEntity<List<CreatorResponse>> getCreatorsByFavorites() {
        List<CreatorResponse> creators = creatorService.getCreatorsByFavorites();
        return ResponseEntity.ok(creators);
    }

    @GetMapping("/main/subscriptions")
    @Operation(summary = "크리에이터 구독 많은 순 상위 3명 목록 조회", description = "크리에이터를 구독 많은 순으로 상위 3명의 목록을 반환합니다.")
    public ResponseEntity<List<CreatorResponse>> getCreatorsBySubscription() {
        List<CreatorResponse> creators = creatorService.getCreatorsBySubscription();
        return ResponseEntity.ok(creators);
    }

    @GetMapping("/category")
    @Operation(summary = "크리에이터 카테고리로 조회", description = "크리에이터를 카테고리 아이디 별로 목록을 반환합니다.")
    public ResponseEntity<List<CreatorResponse>> getCreatorsByCategory(int page, int size, UUID category_id) {
        List<CreatorResponse> creators = creatorService.getCreatorsByCategory(page, size, category_id);
        return ResponseEntity.ok(creators);
    }

    @GetMapping("/latest")
    @Operation(summary = "크리에이터 최신순 조회", description = "크리에이터를 최신순으로 반환합니다.")
    public ResponseEntity<List<CreatorResponse>> getCreatorsOrderByCreatedAt(int page, int size) {
        List<CreatorResponse> creators = creatorService.getCreatorsOrderByCreatedAt(page, size);
        return ResponseEntity.ok(creators);
    }

    @GetMapping("/{creatorId}")
    @Operation(summary = "크리에이터 상세 조회", description = "크리에이터 정보를 반환합니다.")
    public ResponseEntity<CreatorDetailResponse> getCreatorDetail(@PathVariable UUID creatorId) {
        CreatorDetailResponse creatorDetail = creatorService.getCreatorDetail(creatorId);
        return ResponseEntity.ok(creatorDetail);
    }

}
