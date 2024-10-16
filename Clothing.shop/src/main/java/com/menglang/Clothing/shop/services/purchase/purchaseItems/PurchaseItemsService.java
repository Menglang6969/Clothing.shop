package com.menglang.Clothing.shop.services.purchase.purchaseItems;

import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;


public interface PurchaseItemsService {
    public PurchaseItemEntity addItem(PurchaseItemEntity cartItem) throws Exception;
    public PurchaseItemEntity updateCartItem(Long id, PurchaseItemEntity cartItem) throws Exception;
    public PurchaseItemEntity isCartItemExist(PurchaseOrderEntity cart, ProductEntity product, String size) throws Exception;
    public void removeCartItem(Long userId,Long cartItemId) throws Exception;
    public PurchaseItemEntity findCartItemById(Long cartItemId) throws Exception;

}
