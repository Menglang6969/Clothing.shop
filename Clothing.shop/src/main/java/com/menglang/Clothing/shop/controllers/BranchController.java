package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchMapper;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;
import com.menglang.Clothing.shop.dto.branch.BranchResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import com.menglang.Clothing.shop.entity.enums.SortBy;
import com.menglang.Clothing.shop.services.branch.BranchServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/admin/branch")
@RestController
@RequiredArgsConstructor
public class BranchController {

    private static final Logger log = LoggerFactory.getLogger(BranchController.class);
    @Autowired
    private final BranchServiceImpl branchService;
    @Autowired
    private final BranchMapper branchMapper;

    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody BranchRequest data) throws Exception {
        log.info("creating branch invoke ........................");
        return ResponseEntity.ok(branchService.create(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> update(@PathVariable("id") Long id, @Valid @RequestBody BranchRequest data) throws Exception {
        log.info("updating branch invoke ........................{}", id);
        return ResponseEntity.ok(branchService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> delete(@PathVariable("id") Long id) throws Exception {
        log.info("delete branch invoke ........................{}", id);
        return ResponseEntity.ok(branchService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> get(@PathVariable("id") Long id) throws Exception {
        log.info("getting branch invoke ........................{}", id);
        return ResponseEntity.ok(branchService.findBranchById(id));
    }


    @GetMapping("get-all-branch")
    public ResponseEntity<BaseResponse> getAllBranch(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "DESC",required = false) SortBy sortBy,
            @RequestParam(value = "sort-field",defaultValue = "createdAt",required = false) String sortField,
            @RequestParam(value = "query",defaultValue = "",required = false) String query
    ) throws Exception {
        Page<BranchEntity> branchEntities=this.branchService.findAllBranches(page,limit, sortBy,sortField,query);
        List<BranchResponse> listBranchEntity=branchEntities.stream().map(this.branchMapper::toBranchDTO).toList();
        return BaseResponse.success(listBranchEntity,branchEntities,"successful");
    }

}
