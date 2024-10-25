package com.menglang.Clothing.shop.services.category;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;

public interface CategoryInterface {
    public ResponseTemplate create(CategoryRequest categoryRequest);

    public ResponseTemplate update(Long id, CategoryRequest categoryRequest);

    public ResponseTemplate delete(Long id);

    public ResponseTemplate getAll();

    public ResponseTemplate findOne(Long id);
}
