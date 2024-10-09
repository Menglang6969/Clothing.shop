package com.menglang.Clothing.shop.dto.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.enums.Sizes;

import java.util.List;


public record ProductRequest(
        String title,
        String description,
        double price,
        String brand,
        Long category,
        @JsonProperty("discount_price")
        int discountPrice,
        @JsonProperty("discount_percentage")
        int discountPercentage,
        int quantity,
        List<Long> colors,
        @JsonProperty("image_url")
        String imageUrl,
        List<Sizes> sizes
) {


//    private String topLevelCategory;
//    private String secondLevelCategory;
//    private String thirdLevelCategory;

    //private String category;

}
