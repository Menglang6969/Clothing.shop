package com.menglang.Clothing.shop.services.imports;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;

public interface ImportService {

    ResponseTemplate makeImport(ImportRequest request) throws Exception;
    public ResponseTemplate verifyImport(Long id)throws Exception;
    public ResponseTemplate getImport(Long id) throws Exception;
    public ResponseTemplate deleteImport(Long id) throws Exception;
}
