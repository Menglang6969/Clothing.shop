package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    @Query("SELECT c from StockEntity c Where c.branch=?1 and c.product=?2 and c.size=?3 and c.color=?4")
    public StockEntity getStock(BranchEntity branch, ProductEntity product, SizeEntity size, ColorEntity color);

    @Query("SELECT SUM(quantity) FROM StockEntity  WHERE product=?1")
    public Long getCurrentQtyProduct(ProductEntity product);


    @Query("SELECT s FROM StockEntity s " +
            "JOIN s.product p " +
            "WHERE branch=?2 AND (?1 IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', ?1, '%')))")
    public Page<StockEntity> findAllStock(String product,BranchEntity branch,Pageable pageable);


    @Query("SELECT s FROM StockEntity s " +
            "JOIN s.product p " +
            "WHERE branch=?2 AND (?1 IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', ?1, '%')))")
    public List<StockEntity> findAllStocks(String product, BranchEntity branch, Pageable pageable);
}

