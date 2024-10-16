package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity,Long> {

//    @Query("SELECT c FROM CartEntity c WHERE c.user.id=?1")
//    public PurchaseOrderEntity findByUserId(Long userId);
//

}
