package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

//    @GetMapping("/products")
//    public ResponseEntity<Page<ProductEntity>> findProductByCategory(
//            @RequestParam String category,
//            @RequestParam List<String> color,
//            @RequestParam List<Size> size,
//            @RequestParam Integer minPrice,
//            @RequestParam Integer maxPrice,
//            @RequestParam Integer minDiscount,
//            @RequestParam String sort,
//            @RequestParam String stock,
//            @RequestParam Integer pageNumber,
//            @RequestParam Integer pageSize
//    ) {
//        Page<ProductEntity> res = productService.getAllProducts(
//                category,
//                color,
//                size,
//                minPrice,
//                maxPrice,
//                minDiscount,
//                sort,
//                stock,
//                pageNumber,
//                pageSize
//        );
//
//        System.out.println("complete product");
//        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/product/${id}")
//    public ResponseEntity<ProductEntity> findProductById(@PathVariable Long productId) throws Exception {
//        ProductEntity product = productService.getProductById(productId);
//        return new ResponseEntity<ProductEntity>(product,HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/product/search")
//    public ResponseEntity<List<ProductEntity>> searchProduct(
//            @RequestParam String q
//    ) throws Exception {
//        List<ProductEntity> product=productService.searchProduct(q);
//        return new ResponseEntity<List<ProductEntity>>(product,HttpStatus.ACCEPTED);
//    }
}
