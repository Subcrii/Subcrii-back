package com.subcrii.subcrii.domain.creator.dto;

import com.subcrii.subcrii.domain.creator.entity.Category;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private UUID id;
    private String name;

    public static CategoryResponse create(Category category) {
        return new CategoryResponse(category);
    }

    private CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
