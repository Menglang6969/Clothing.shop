package com.menglang.Clothing.shop.dto.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.export.details.ExportDetailsRequest;

import java.util.List;

public record ExportRequest(
        @JsonProperty("export_no")
        String exportNo,

        @JsonProperty("from_branch")
        Long fromBranch,

        @JsonProperty("to_branch")
        Long toBranch,

        @JsonProperty("export_details")
        List<ExportDetailsRequest> exportDetails
) {

}
