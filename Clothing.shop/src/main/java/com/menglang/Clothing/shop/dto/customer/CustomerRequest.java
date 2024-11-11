package com.menglang.Clothing.shop.dto.customer;

import lombok.Builder;

@Builder
public record CustomerRequest(
        String name,
        String phone,
        String address
) {
}
