package com.menglang.Clothing.shop.services.category;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import org.springframework.data.domain.Page;

public interface CategoryInterface {
    public ResponseTemplate create(CategoryRequest categoryRequest);

    public ResponseTemplate update(Long id, CategoryRequest categoryRequest);

    public ResponseTemplate delete(Long id);

    public Page<CategoryEntity> getAll(int page, int limit, String sort, String query);

    public ResponseTemplate findOne(Long id);
}
