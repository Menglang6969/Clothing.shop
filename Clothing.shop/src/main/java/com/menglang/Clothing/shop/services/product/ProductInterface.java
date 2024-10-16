package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInterface {
      ResponseErrorTemplate create(ProductRequest product) throws Exception;
     List<ProductEntity> getProducts() throws Exception;

     ResponseErrorTemplate getProductById(Long id) throws Exception;

    ResponseErrorTemplate updateProduct(Long id, ProductRequest product) throws Exception;
     ResponseErrorTemplate deleteProduct(Long id) throws Exception;
     List<ProductEntity> getProductsByCategory(String category) throws Exception;
    List<ProductEntity> searchProduct(String query) throws Exception;
     Page<BasePageResponse> getAllProducts(
             Long categoryId,
             Double minPrice,
             Double maxPrice,
             List<Long> colorIds,
             List<Long> sizeIds,
             int pageNumber,
             int pageSize,
             String sortBy,
             String sortDir

    );
}
