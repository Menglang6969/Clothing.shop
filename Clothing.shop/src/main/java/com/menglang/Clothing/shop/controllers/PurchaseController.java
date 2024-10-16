package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.cart.ItemRequest;
import com.menglang.Clothing.shop.services.cart.cart.CartServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/cart")
@RequiredArgsConstructor
public class PurchaseController {

    private final CartServiceImpl cartService;



    @PostMapping
    public ResponseEntity<ResponseErrorTemplate> addItems(ItemRequest itemRequest) throws Exception{
            return ResponseEntity.ok(cartService.CreateCart());
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseErrorTemplate> getCartItems(){
        return ResponseEntity.ok(cartService);
    }
}
