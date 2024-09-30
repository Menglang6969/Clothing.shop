package com.menglang.Clothing.shop.services.cart.cart;

import com.menglang.Clothing.shop.dto.cart.ItemRequest;
import com.menglang.Clothing.shop.entity.CartEntity;
import com.menglang.Clothing.shop.entity.UserEntity;

public interface CartService {
    public CartEntity CreateCart(UserEntity user) throws Exception;
    public String addCartItem(Long id, ItemRequest request) throws Exception;
    public CartEntity findUserCart(Long userId) throws Exception;

}
