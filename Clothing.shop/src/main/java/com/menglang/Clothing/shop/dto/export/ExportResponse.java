package com.menglang.Clothing.shop.dto.export;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.export.details.ExportDetailsResponse;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExportResponse extends BasePageResponse {
    Long id;
    String exportNo;
    BranchDTO fromBranch;
    BranchDTO toBranch;
    List<ExportDetailsResponse> exportDetails;
}
