package com.menglang.Clothing.shop.services.rating;

import com.menglang.Clothing.shop.dto.rating.RatingRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.RatingEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.RatingRepository;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService{

    @Autowired
    private final RatingRepository ratingRepository;

    @Autowired
    private final ProductServiceImpl productService;


    @Override
    public RatingEntity createRating(RatingRequest request, UserEntity user) throws Exception {
       try{
           ProductEntity product=productService.getProductById(request.productId());
           RatingEntity rating=RatingEntity.builder()
                   .user(user)
                   .product(product)
                   .rate(request.rating())
                   .build();
           return ratingRepository.save(rating);
       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"500");
       }
    }

    @Override
    public List<RatingEntity> getProductsRating(Long productId) throws Exception {
        return ratingRepository.getAllProductsRating(productId);
    }
}
