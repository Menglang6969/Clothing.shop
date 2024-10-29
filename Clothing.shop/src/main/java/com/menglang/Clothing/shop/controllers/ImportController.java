package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.export.ExportResponse;
import com.menglang.Clothing.shop.dto.imports.ImportMapper;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.dto.imports.ImportResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.ExportEntity;
import com.menglang.Clothing.shop.entity.ImportEntity;
import com.menglang.Clothing.shop.services.imports.ImportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/import")
@RequiredArgsConstructor
public class ImportController {
    private static final Logger log = LoggerFactory.getLogger(ImportController.class);
    @Autowired
    private final ImportService importService;
    private final ImportMapper importMapper;

    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody ImportRequest data) throws Exception {
        return ResponseEntity.ok(importService.makeImport(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> get(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(importService.getImport(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> verify(@PathVariable("id") Long id) throws Exception{
        log.info("invoke verify import .......................");
        return ResponseEntity.ok(importService.verifyImport(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> drop(@PathVariable("id") Long id) throws Exception{
        log.info("invoke drop import...........................");
        return ResponseEntity.ok(importService.deleteImport(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "desc:createdAt",required = false) String sortBy,
            @RequestParam(value = "importNo",defaultValue = "",required = false) String code
    ) throws Exception {
        Page<ImportEntity> importEntities=importService.getAll(page,limit,sortBy,code);
        List<ImportResponse> importResponses=importEntities.stream().map(this.importMapper::toImportDTO).toList();
        return BaseResponse.success( importResponses,importEntities, "success");
    }
}
