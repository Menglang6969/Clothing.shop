package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemsEntity,Long> {
}
