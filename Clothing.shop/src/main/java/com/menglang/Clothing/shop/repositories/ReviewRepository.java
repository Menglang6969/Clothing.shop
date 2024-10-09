package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    @Query("SELECT r FROM ReviewEntity r WHERE r.product.id=?1")
    public List<ReviewEntity> getAllProductsReview(Long productId);
}
