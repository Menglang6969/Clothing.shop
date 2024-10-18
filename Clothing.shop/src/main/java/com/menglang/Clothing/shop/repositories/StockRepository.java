package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity,Long> {
}
