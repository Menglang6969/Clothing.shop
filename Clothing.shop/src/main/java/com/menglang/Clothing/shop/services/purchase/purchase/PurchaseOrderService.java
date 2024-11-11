package com.menglang.Clothing.shop.services.purchase.purchase;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;

public interface PurchaseOrderService {
    public ResponseTemplate createPurchase(PurchaseOrderRequest user) throws Exception;
    public ResponseTemplate editPurchase(Long id, PurchaseOrderRequest request) throws Exception;
    public ResponseTemplate dropPurchase(Long id)throws Exception;

}
