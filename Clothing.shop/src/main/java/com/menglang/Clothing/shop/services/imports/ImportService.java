package com.menglang.Clothing.shop.services.imports;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.dto.imports.ImportResponse;

public interface ImportService {

    ResponseErrorTemplate makeImport(ImportRequest request) throws Exception;
    public ResponseErrorTemplate verifyImport(Long id)throws Exception;
    public ResponseErrorTemplate getImport(Long id) throws Exception;
    public ResponseErrorTemplate deleteImport(Long id) throws Exception;
}
