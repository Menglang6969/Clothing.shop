package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductInterface{
    @Override
    public void create(ProductRequest product) {

    }

    @Override
    public List<ProductEntity> getProducts() {
        return List.of();
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return null;
    }

    @Override
    public ProductEntity updateProduct(Long id, ProductEntity product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public List<ProductEntity> getProductsByCategory(String category) {
        return List.of();
    }
}
