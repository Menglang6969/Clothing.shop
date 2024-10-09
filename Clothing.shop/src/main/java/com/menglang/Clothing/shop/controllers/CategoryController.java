package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.category.CategoryRequest;
import com.menglang.Clothing.shop.services.category.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryServiceImpl categoryService;

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
}
