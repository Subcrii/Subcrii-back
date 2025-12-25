package com.subcrii.subcrii.domain.creator.service;

import com.subcrii.subcrii.domain.creator.dto.CategoryResponse;
import com.subcrii.subcrii.domain.creator.entity.Category;
import com.subcrii.subcrii.domain.creator.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategoryList() {
        List<Category> categoryList = categoryRepository.getAllCategory();
        return categoryList.stream()
                .map(CategoryResponse :: create)
                .toList();
    }


}
