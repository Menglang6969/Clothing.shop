package com.menglang.Clothing.shop.dto.category;

import com.menglang.Clothing.shop.entity.CategoryEntity;

public record CategoryRequest(
        String  name,
        Long parent,
        int level,
        String description

) {
}
