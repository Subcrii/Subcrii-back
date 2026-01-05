package com.subcrii.subcrii.domain.content.controller;

import com.subcrii.subcrii.domain.content.dto.ContentDetailResponse;
import com.subcrii.subcrii.domain.content.dto.ContentListResponse;
import com.subcrii.subcrii.domain.content.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/creators")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping("/{creatorId}/contents")
    @Operation(summary = "컨텐츠 목록 조회", description = "선택한 크리에이터의 컨텐츠 목록을 조회할 수 있습니다.")
    public ResponseEntity<Page<ContentListResponse>> getContentList(
            @PathVariable UUID creatorId,
            Pageable pageable
    ) {
        Page<ContentListResponse> contentListResponses = contentService.getContentList(creatorId, pageable);
        return ResponseEntity.ok(contentListResponses);
    }

    @GetMapping("/{creatorId}/contents/{contentId}")
    @Operation(summary = "컨텐츠 상세 조회", description = "구독한 크리에이터의 컨텐츠 내용을 조회할 수 있습니다.")
    public ResponseEntity<ContentDetailResponse> getContentDetail(
            @PathVariable UUID creatorId,
            @PathVariable UUID contentId
    ) {
        ContentDetailResponse contentDetailResponse = contentService.getContentDetail(creatorId, contentId);
        return ResponseEntity.ok(contentDetailResponse);
    }
}
