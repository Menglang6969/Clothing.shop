package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    public Optional<CategoryEntity> findByName(String name);


    @Query("Select c from CategoryEntity c Where c.name=?1 And c.parentId.name=?2")
    public CategoryEntity findByNameAndParent(
            String name,
            String parentCategoryName
    );

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CategoryEntity c WHERE c.name = ?1 AND c.id != ?2")
    public boolean existsByNameAndIdNot(String name, Long id);

    @Query("Select  CASE WHEN COUNT(c) > 0 THEN true ELSE false END from CategoryEntity c Where c.name=?1")
    public boolean existsByName(String name);
}
