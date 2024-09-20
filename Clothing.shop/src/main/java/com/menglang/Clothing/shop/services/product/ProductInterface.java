package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInterface {
      ResponseErrorTemplate create(ProductRequest product) throws Exception;
     List<ProductEntity> getProducts() throws Exception;
     ProductEntity getProductById(Long id) throws Exception;
     ResponseErrorTemplate updateProduct(Long id, ProductEntity product) throws Exception;
     ResponseErrorTemplate deleteProduct(Long id) throws Exception;
     List<ProductEntity> getProductsByCategory(String category) throws Exception;

     Page<ProductEntity> getAllProducts(
      String category,
      List<String> colors,
      List<Size> sizes,
      Integer minPrice,
      Integer maxPrice,
      Integer discount,
      String sort,
      String stock,
      Integer pageNumber,
      Integer pageSize
    );
}
