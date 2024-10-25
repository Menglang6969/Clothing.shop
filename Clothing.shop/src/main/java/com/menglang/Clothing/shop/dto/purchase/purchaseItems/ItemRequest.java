package com.menglang.Clothing.shop.dto.purchase.purchaseItems;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ItemRequest(
        @JsonProperty("product_id")
        Long productId,
        Long size,
        Long color,
        Integer quantity,
        Double price,
        @JsonProperty("discounted_price")
        double discountedPrice,
        @JsonProperty("discounted_percent")
        int discountedPercent
) {
}
