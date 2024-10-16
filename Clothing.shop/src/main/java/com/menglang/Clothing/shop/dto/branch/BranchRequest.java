package com.menglang.Clothing.shop.dto.branch;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record BranchRequest(
        String name,
        String address,
        String description
) {
}
