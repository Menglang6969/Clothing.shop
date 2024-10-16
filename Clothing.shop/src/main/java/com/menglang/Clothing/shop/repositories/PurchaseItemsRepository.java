package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseItemsRepository extends JpaRepository<PurchaseItemEntity,Long> {

//    @Query("SELECT ci FROM PurchaseItemEntity ci WHERE ci.cart=?1 AND ci.product=?2 AND ci.size=?3 ")
//    public PurchaseItemEntity isCartItemExist(
//            PurchaseOrderEntity cart,
//            ProductEntity product,
//            String size
//    );
}
