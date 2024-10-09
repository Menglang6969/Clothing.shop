package com.menglang.Clothing.shop.services.rating;

import com.menglang.Clothing.shop.dto.rating.RatingRequest;
import com.menglang.Clothing.shop.entity.RatingEntity;
import com.menglang.Clothing.shop.entity.UserEntity;

import java.util.List;

public interface RatingService {
    public RatingEntity createRating(RatingRequest request, UserEntity user) throws Exception;
    public List<RatingEntity> getProductsRating(Long productId) throws Exception;
}
