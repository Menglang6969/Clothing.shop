package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;

import java.util.List;

public interface ProductInterface {
    public  void create(ProductRequest product);
    public List<ProductEntity> getProducts();
    public ProductEntity getProductById(Long id);
    public ProductEntity updateProduct(Long id, ProductEntity product);
    public void deleteProduct(Long id);
    public List<ProductEntity> getProductsByCategory(String category);
}
