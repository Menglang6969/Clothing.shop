package com.menglang.Clothing.shop.services.category;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;

public interface CategoryInterface {
    public ResponseErrorTemplate create(CategoryRequest categoryRequest);

    public ResponseErrorTemplate update(Long id, CategoryRequest categoryRequest);

    public ResponseErrorTemplate delete(Long id);

    public ResponseErrorTemplate getAll();
}
