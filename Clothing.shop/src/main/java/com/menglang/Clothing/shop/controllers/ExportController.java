package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeResponse;
import com.menglang.Clothing.shop.dto.export.ExportMapper;
import com.menglang.Clothing.shop.dto.export.ExportRequest;
import com.menglang.Clothing.shop.dto.export.ExportResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import com.menglang.Clothing.shop.entity.ExportEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import com.menglang.Clothing.shop.services.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/export")
@RequiredArgsConstructor
public class ExportController {
    private static final Logger log = LoggerFactory.getLogger(ExportController.class);
    @Autowired
    private final ExportService exportService;
    private final ExportMapper exportMapper;

    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody ExportRequest data) throws Exception {
        return ResponseEntity.ok(exportService.makeExport(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> get(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(exportService.getExport(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> verify(@PathVariable("id") Long id) throws Exception{
        log.info("invoke verify import .......................");
        return ResponseEntity.ok(exportService.verifyExport(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> drop(@PathVariable("id") Long id) throws Exception{
        log.info("invoke drop import...........................");
        return ResponseEntity.ok(exportService.deleteExport(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "desc:createdAt",required = false) String sortBy,
            @RequestParam(value = "exportNo",defaultValue = "INCOME",required = false) String code
    ) throws Exception {
        Page<ExportEntity> exportEntities=exportService.getAll(page,limit,sortBy,code);
        List<ExportResponse> exportResponses=exportEntities.stream().map(this.exportMapper::toExportDTO).toList();
        return BaseResponse.success( exportResponses,exportEntities, "success");
    }
}
