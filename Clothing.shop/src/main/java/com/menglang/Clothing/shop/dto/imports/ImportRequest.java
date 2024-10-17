package com.menglang.Clothing.shop.dto.imports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;

import java.util.List;

public record ImportRequest(
        @JsonProperty("import_no")
        String importNo,
        Long branch,
        List<ImportDetailsRequest> importDetails
) {
}
