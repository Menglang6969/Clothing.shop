package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("select o from OrderEntity o where upper(o.orderNo) like upper(concat('%', ?1, '%'))")
    BaseResponse findByOrderNoContainingIgnoreCase(String orderNo);

    @Query("SELECT o FROM OrderEntity o WHERE (?1 IS NULL OR o.branch = ?1) AND o.createdAt >= ?2 AND o.createdAt <= ?3")
    Page<OrderEntity> findAllByBranchAndCreatedAtBetween(BranchEntity branch, Date createdAt, Date endDate, Pageable pageable);
}
