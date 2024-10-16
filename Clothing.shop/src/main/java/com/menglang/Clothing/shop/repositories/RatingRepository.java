package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity,Long> {

    @Query("SELECT r FROM RatingEntity r WHERE r.product.id=?1")
    public List<RatingEntity> getAllProductsRating(Long productId);

}
