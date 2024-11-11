package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.branch.BranchResponse;
import com.menglang.Clothing.shop.dto.category.CategoryMapper;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;
import com.menglang.Clothing.shop.dto.category.CategoryResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.enums.SortBy;
import com.menglang.Clothing.shop.services.category.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryServiceImpl categoryService;
    @Autowired
    private final CategoryMapper categoryMapper;

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody CategoryRequest category){
        log.info("data category :{}",category);
        Object data=categoryService.create(category);
        return  ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id,@RequestBody CategoryRequest category){
        log.info("data category to update :{}",category);
        Object data=categoryService.update(id,category);
        return  ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
        log.info("data category id to delete :{}",id);
        Object data=categoryService.delete(id);
        return  ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) throws Exception{
        log.info("data category id to find :{}",id);
        Object data=categoryService.findOne(id);
        return  ResponseEntity.ok(data);
    }

    @GetMapping("get-all-category")
    public ResponseEntity<BaseResponse> getAllBranch(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "desc:createdAt",required = false) String sortBy,
            @RequestParam(value = "sort-field",defaultValue = "createdAt",required = false) String sortField,
            @RequestParam(value = "query",defaultValue = "",required = false) String query
    ) throws Exception {
        Page<CategoryEntity> categoryEntities=this.categoryService.getAll(page,limit, sortBy,query);
        List<CategoryResponse> listCategory=categoryEntities.stream().map(this.categoryMapper::toCategoryDTO).toList();
        return BaseResponse.success(listCategory,categoryEntities,"successful");
    }
}
