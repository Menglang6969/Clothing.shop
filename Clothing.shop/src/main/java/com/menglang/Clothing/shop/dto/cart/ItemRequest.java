package com.menglang.Clothing.shop.dto.cart;


public record ItemRequest(
    Long productId,
    String size,
    int quantity,
    Integer price
) {
}
