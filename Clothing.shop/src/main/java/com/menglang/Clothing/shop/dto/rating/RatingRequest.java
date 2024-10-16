package com.menglang.Clothing.shop.dto.rating;

public record RatingRequest(
    Long productId,
    double rating
) {
}
