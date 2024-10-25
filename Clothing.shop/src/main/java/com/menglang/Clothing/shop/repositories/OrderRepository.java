package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
