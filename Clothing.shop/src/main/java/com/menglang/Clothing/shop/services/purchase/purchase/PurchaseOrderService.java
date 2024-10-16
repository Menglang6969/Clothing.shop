package com.menglang.Clothing.shop.services.purchase.purchase;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.entity.UserEntity;

public interface PurchaseOrderService {
    public ResponseErrorTemplate createPurchase(PurchaseOrderRequest user) throws Exception;
    public String editPurchase(Long id, ItemRequest request) throws Exception;
    public PurchaseOrderEntity findUserCart(Long userId) throws Exception;

}
