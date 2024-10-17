package com.menglang.Clothing.shop.dto.imports;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ImportResponse extends BasePageResponse {
    Long id;
    String importNo;
    BranchDTO branch;
    List<ImportDetailsResponse> importDetails;
}
