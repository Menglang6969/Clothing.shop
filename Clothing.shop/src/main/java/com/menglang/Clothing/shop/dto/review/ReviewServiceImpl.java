package com.menglang.Clothing.shop.dto.review;

import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.ReviewEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.repositories.ReviewRepository;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import com.menglang.Clothing.shop.services.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private final ReviewRepository reviewRepository;

    @Autowired
    private final ProductServiceImpl productService;

    @Autowired
    private final ProductRepository productRepository;


    @Override
    public ReviewEntity createReview(ReviewRequest request, UserEntity user) throws Exception {
        try{
            ProductEntity product=productService.findProductById(request.productId());
            ReviewEntity review=ReviewEntity.builder()
                    .user(user)
                    .product(product)
                    .review(request.review())
                    .build();
            return reviewRepository.save(review);
        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"500");
        }
    }

    @Override
    public List<ReviewEntity> getAllReview(Long productId) {

        return reviewRepository.getAllProductsReview(productId);
    }
}
