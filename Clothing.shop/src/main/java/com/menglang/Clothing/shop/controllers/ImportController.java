package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.services.imports.ImportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/import")
@RequiredArgsConstructor
public class ImportController {
    private static final Logger log = LoggerFactory.getLogger(ImportController.class);
    @Autowired
    private final ImportService importService;

    @PostMapping
    public ResponseEntity<ResponseErrorTemplate> create(@RequestBody ImportRequest data) throws Exception {
        return ResponseEntity.ok(importService.makeImport(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> get(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(importService.getImport(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> verify(@PathVariable("id") Long id) throws Exception{
        log.info("invoke verify import .......................");
        return ResponseEntity.ok(importService.verifyImport(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> drop(@PathVariable("id") Long id) throws Exception{
        log.info("invoke drop import...........................");
        return ResponseEntity.ok(importService.deleteImport(id));
    }
}
