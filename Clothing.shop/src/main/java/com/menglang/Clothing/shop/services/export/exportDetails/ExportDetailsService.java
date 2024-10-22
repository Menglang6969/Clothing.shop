package com.menglang.Clothing.shop.services.export.exportDetails;

import com.menglang.Clothing.shop.dto.export.details.ExportDetailsRequest;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.entity.ExportDetailsEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;

public interface ExportDetailsService {
    public ExportDetailsEntity create(ExportDetailsRequest data) throws Exception;
    public ExportDetailsEntity update(Long id, ExportDetailsRequest request) throws Exception;
}
