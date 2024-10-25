package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.export.ExportRequest;
import com.menglang.Clothing.shop.services.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/export")
@RequiredArgsConstructor
public class ExportController {
    private static final Logger log = LoggerFactory.getLogger(ExportController.class);
    @Autowired
    private final ExportService exportService;

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
}
