package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    public CategoryEntity findByName(String name);

    @Query("Select c from category_tbl c Where c.name=:name And c.parentCategory.name=:parentCategoryName")
    public CategoryEntity findByNameAndParent(
            @Param("name") String name,
            @Param("parentCategoryName") String parentCategoryName
    );
}
