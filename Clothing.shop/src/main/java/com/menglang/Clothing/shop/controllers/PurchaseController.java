package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.services.purchase.purchase.PurchaseOrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/cart")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseOrderServiceImpl purchaseOrderService;



    @PostMapping
    public ResponseEntity<ResponseErrorTemplate> addItems(@Valid PurchaseOrderRequest itemRequest) throws Exception{
            return ResponseEntity.ok(purchaseOrderService.createPurchase(itemRequest));
    }

//    @GetMapping("/user")
//    public ResponseEntity<ResponseErrorTemplate> getCartItems(){
//
//    }
}
