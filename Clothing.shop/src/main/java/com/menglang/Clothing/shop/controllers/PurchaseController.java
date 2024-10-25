package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.services.purchase.purchase.PurchaseOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseOrderServiceImpl purchaseOrderService;

    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody PurchaseOrderRequest itemRequest) throws Exception {
        return ResponseEntity.ok(purchaseOrderService.createPurchase(itemRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> update(@PathVariable("id") Long id, @RequestBody PurchaseOrderRequest itemRequest) throws Exception {
        return ResponseEntity.ok(purchaseOrderService.editPurchase(id, itemRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTemplate> dropPurchase(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(purchaseOrderService.dropPurchase(id));
    }


}
