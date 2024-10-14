package com.menglang.Clothing.shop.dto.category;

import com.menglang.Clothing.shop.entity.CategoryEntity;

public record CategoryDTO(Long id, String name, CategoryDTO parentId) {
}
