package com.menglang.Clothing.shop.dto.export.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ExportDetailsRequest(
        @JsonProperty("product")
        Long product,

        @JsonProperty("size")
        Long size,

        @JsonProperty("color")
        Long color,

        int quantity
) {
}
