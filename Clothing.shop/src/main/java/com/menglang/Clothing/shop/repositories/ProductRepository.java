package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Long, ProductEntity> {

   List<ProductEntity> findByTitle(String title);

    List<ProductEntity> findProductByCategory(String name);

}
