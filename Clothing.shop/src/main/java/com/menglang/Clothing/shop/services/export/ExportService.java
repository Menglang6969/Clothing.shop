package com.menglang.Clothing.shop.services.export;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.export.ExportRequest;

public interface ExportService {
    ResponseTemplate makeExport(ExportRequest request) throws Exception;
    public ResponseTemplate verifyExport(Long id)throws Exception;
    public ResponseTemplate getExport(Long id) throws Exception;
    public ResponseTemplate deleteExport(Long id) throws Exception;
}
