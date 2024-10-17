package com.menglang.Clothing.shop.dto.imports.details;

import com.menglang.Clothing.shop.dto.product.ProductDTO;
import lombok.Builder;
import lombok.Setter;

@Builder
public record ImportDetailsResponse(
        ProductDTO product,
        String size,
        String color,
        int quantity,
        double importCost

) {
}
