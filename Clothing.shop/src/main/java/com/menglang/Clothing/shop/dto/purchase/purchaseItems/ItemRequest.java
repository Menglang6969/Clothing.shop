package com.menglang.Clothing.shop.dto.purchase.purchaseItems;


import jakarta.validation.Valid;
import lombok.NonNull;

public record ItemRequest(
    @NonNull
    Long productId,

    @NonNull
    Long size,

    @NonNull
    Long color,

    @NonNull
    Integer quantity,

    @NonNull
    Double price,
    int discountedPrice,
    int discountedPercent
) {
}
