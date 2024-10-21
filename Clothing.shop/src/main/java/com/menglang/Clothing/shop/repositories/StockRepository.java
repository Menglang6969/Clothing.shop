package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    @Query("SELECT c from StockEntity c Where c.branch=?1 and c.product=?2 and c.size=?3 and c.color=?4")
    public StockEntity getStock(BranchEntity branch, ProductEntity product, SizeEntity size, ColorEntity color);

    @Query("SELECT SUM(quantity) FROM StockEntity  WHERE product=?1")
    public Long getCurrentQtyProduct(ProductEntity product);
}

