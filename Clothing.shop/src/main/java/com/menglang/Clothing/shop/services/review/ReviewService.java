package com.menglang.Clothing.shop.services.review;

import com.menglang.Clothing.shop.dto.review.ReviewRequest;
import com.menglang.Clothing.shop.entity.ReviewEntity;
import com.menglang.Clothing.shop.entity.UserEntity;

import java.util.List;

public interface ReviewService {
    public ReviewEntity createReview(ReviewRequest request, UserEntity user) throws Exception;
    public List<ReviewEntity> getAllReview(Long productId);
}
