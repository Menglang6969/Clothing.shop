package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    public CategoryEntity findByName(String name);


    @Query("Select c from CategoryEntity c Where c.name=?1 And c.parent.name=?2")
    public CategoryEntity findByNameAndParent(
            String name,
          String parentCategoryName
    );
}
