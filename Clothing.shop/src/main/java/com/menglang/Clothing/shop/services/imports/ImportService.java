package com.menglang.Clothing.shop.services.imports;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.entity.ExportEntity;
import com.menglang.Clothing.shop.entity.ImportEntity;
import org.springframework.data.domain.Page;

public interface ImportService {

    ResponseTemplate makeImport(ImportRequest request) throws Exception;
    public ResponseTemplate verifyImport(Long id)throws Exception;
    public ResponseTemplate getImport(Long id) throws Exception;
    public ResponseTemplate deleteImport(Long id) throws Exception;
    public Page<ImportEntity> getAll(int page, int limit, String sortBy, String query);
}
