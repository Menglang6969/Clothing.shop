package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CartEntity;
import com.menglang.Clothing.shop.entity.CartItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {

    @Query("SELECT ci FROM CartItemEntity ci WHERE ci.cart=?1 AND ci.product=?2 AND ci.size=?3 ")
    public CartItemEntity isCartItemExist(
            CartEntity cart,
            ProductEntity product,
            String size
    );
}
