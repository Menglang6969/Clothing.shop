package com.menglang.Clothing.shop.dto.export.details;

import com.menglang.Clothing.shop.dto.product.ProductDTO;
import lombok.Builder;

@Builder
public record ExportDetailsResponse(
        ProductDTO product,
        String size,
        String color,
        int quantity

) {
}
