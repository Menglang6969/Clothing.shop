package com.menglang.Clothing.shop.services.imports.importDetails;

import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;

import java.util.List;

public interface ImportDetailsService {

    public ImportDetailsEntity create(ImportDetailsRequest data) throws Exception;
    public ImportDetailsEntity update(Long id, ImportDetailsRequest request) throws Exception;
}
