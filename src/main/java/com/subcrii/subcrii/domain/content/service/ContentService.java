package com.subcrii.subcrii.domain.content.service;

import com.subcrii.subcrii.domain.content.dto.ContentListResponse;
import com.subcrii.subcrii.domain.content.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    @Transactional(readOnly = true)
    public Page<ContentListResponse> getContentList(UUID creatorId, Pageable pageable) {
        return contentRepository.findContentListByCreatorId(creatorId, pageable);
    }
}
