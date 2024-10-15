package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    //    @Query("SELECT p FROM ProductEntity p " +
//            "WHERE (p.category.name = ?1 OR ?1 = '') " +
//            "AND ((?2 IS NULL AND ?3 IS NULL) " +
//            "     OR (p.discountedPrice BETWEEN ?2 AND ?3)) " +
//            "AND (?4 IS NULL OR p.discountedPrice >= ?4) " +
//            "ORDER BY " +
//            "  CASE WHEN ?5 = 'price_low' THEN p.discountedPercent END ASC," +
//            "  CASE WHEN ?5 = 'price_high' THEN p.discountedPercent END DESC"
//    )
//    @Query("SELECT p FROM ProductEntity p " +
//            "JOIN p.category c " +
//            "JOIN p.colors co " +
//            "WHERE (?1 IS NULL OR c.id = ?1) " +
//            "AND (?2 IS NULL OR p.sellCost >= ?2) " +
//            "AND (?3 IS NULL OR p.sellCost <= ?3) " +
//            "AND (?4 IS NULL OR co.id IN ?4)")
    @Query("select p from ProductEntity p ")

    Page<ProductEntity> filterProducts(
            Pageable pageable,
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            List<Long> colorIds

    );


}
