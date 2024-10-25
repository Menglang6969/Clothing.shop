package com.menglang.Clothing.shop.dto.order.orderDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record OrderDetailsRequest(
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