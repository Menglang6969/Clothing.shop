package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long > {

    @Query("SELECT p FROM product_tbl p" +" WHERE (p.category.name=:category OR :category='')"+
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))"+
            "AND (:minDiscount IS NULL OR p.discountPercent >= :discount)"+
            "ORDER BY"+
            "CASE WHEN :sort='price_low' THEN p.discountedPrice END ASC,"+
            "CASE WHEN :sort='price_high' THEN p.discountedPrice END DESC"

            )
    public List<ProductEntity> filterProducts(@Param("category") String category,
                                              @Param("minPrice") Integer minPrice,
                                              @Param("maxPrice") Integer maxPrice,
                                              @Param("discount") String discount,
                                              @Param("sort") String sort
                                              );


}
