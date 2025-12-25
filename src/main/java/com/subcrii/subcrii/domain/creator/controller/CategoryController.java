package com.subcrii.subcrii.domain.creator.controller;

import com.subcrii.subcrii.domain.creator.dto.CategoryResponse;
import com.subcrii.subcrii.domain.creator.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category/all")
    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 반환합니다.")
    public ResponseEntity<List<CategoryResponse>> getCategoryList(){
        return ResponseEntity.ok(categoryService.getCategoryList());
    }
}
