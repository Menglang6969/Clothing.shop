package com.menglang.Clothing.shop.dto.imports.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ImportDetailsRequest(
        @JsonProperty("product")
        Long product,

        @JsonProperty("size")
        Long size,

        @JsonProperty("color")
        Long color,

        int quantity,

        @JsonProperty("import_cost")
        double importCost

) {
}
