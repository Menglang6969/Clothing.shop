package com.menglang.Clothing.shop.services.cart.cartItem;

import com.menglang.Clothing.shop.dto.cart.ItemRequest;
import com.menglang.Clothing.shop.entity.CartEntity;
import com.menglang.Clothing.shop.entity.CartItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;


public interface CartItemService {
    public CartItemEntity createCartItem(CartItemEntity cartItem) throws Exception;
    public CartItemEntity updateCartItem(Long id, CartItemEntity cartItem) throws Exception;
    public CartItemEntity isCartItemExist(CartEntity cart, ProductEntity product,String size) throws Exception;
    public void removeCartItem(Long userId,Long cartItemId) throws Exception;
    public CartItemEntity findCartItemById(Long cartItemId) throws Exception;

}
