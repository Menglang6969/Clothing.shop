package com.menglang.Clothing.shop.services.export;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.export.ExportRequest;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;

public interface ExportService {
    ResponseErrorTemplate makeExport(ExportRequest request) throws Exception;
    public ResponseErrorTemplate verifyExport(Long id)throws Exception;
    public ResponseErrorTemplate getExport(Long id) throws Exception;
    public ResponseErrorTemplate deleteExport(Long id) throws Exception;
}
