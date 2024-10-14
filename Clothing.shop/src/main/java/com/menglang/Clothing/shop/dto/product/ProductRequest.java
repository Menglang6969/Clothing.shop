package com.menglang.Clothing.shop.dto.product;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;


public record ProductRequest(
        String title,
        String description,
        @JsonProperty("base_cost")
        BigDecimal baseCost,
        @JsonProperty("sell_cost")
        BigDecimal sellCost,
        Long category,
        @JsonProperty("discounted_price")
        int discountedPrice,
        @JsonProperty("discounted_percent")
        int discountedPercent,
        @JsonProperty("image_url")
        String imageUrl,
        List<Long> colors,
        List<Long> sizes
) {


}
