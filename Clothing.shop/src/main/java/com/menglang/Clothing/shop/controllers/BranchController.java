package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;
import com.menglang.Clothing.shop.services.branch.BranchServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/admin/branch")
@RestController
@RequiredArgsConstructor
public class BranchController {

    private static final Logger log = LoggerFactory.getLogger(BranchController.class);
    @Autowired
    private final BranchServiceImpl branchService;

    @PostMapping
    public ResponseEntity<ResponseErrorTemplate> create( @RequestBody BranchRequest data) throws Exception {
        log.info("creating branch invoke ........................");
        return ResponseEntity.ok(branchService.create(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> update(@PathVariable("id") Long id, @Valid @RequestBody BranchRequest data) throws Exception {
        log.info("updating branch invoke ........................{}", id);
        return ResponseEntity.ok(branchService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> delete(@PathVariable("id") Long id) throws Exception {
        log.info("delete branch invoke ........................{}", id);
        return ResponseEntity.ok(branchService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseErrorTemplate> get(@PathVariable("id") Long id) throws Exception {
        log.info("getting branch invoke ........................{}", id);
        return ResponseEntity.ok(branchService.findBranchById(id));
    }

}
