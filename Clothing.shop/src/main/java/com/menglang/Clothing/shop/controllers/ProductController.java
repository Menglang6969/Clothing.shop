package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductServiceImpl productService;


    @PostMapping("/product")
    public ResponseEntity<ResponseErrorTemplate> create(@RequestBody ProductRequest data){
        log.info(" invoke creating product .............");
        return ResponseEntity.ok(productService.create(data));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductEntity>> findProductByCategory(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<Long> colorIds,
            @RequestParam(required = false) List<Long> sizeIds,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        log.info(" invoke getting products .............");
        Page<ProductEntity> res = productService.getAllProducts(
                categoryId,
                minPrice,
                maxPrice,
                colorIds,
                sizeIds,
                pageNumber,
                pageSize,
                sortBy,
                sortDir

        );

        System.out.println("complete product");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseErrorTemplate> findProductById(@PathVariable("id") Long productId) throws Exception {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

//    @GetMapping("/product/search")
//    public ResponseEntity<List<ProductEntity>> searchProduct(
//            @RequestParam String q
//    ) throws Exception {
//        List<ProductEntity> product=productService.searchProduct(q);
//        return new ResponseEntity<List<ProductEntity>>(product,HttpStatus.ACCEPTED);
//    }
}
