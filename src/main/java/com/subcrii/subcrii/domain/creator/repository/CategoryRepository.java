package com.subcrii.subcrii.domain.creator.repository;

import com.subcrii.subcrii.domain.creator.entity.Category;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query(value = "SELECT c "
                    + "FROM Category c")
    List<Category> getAllCategory();
}
