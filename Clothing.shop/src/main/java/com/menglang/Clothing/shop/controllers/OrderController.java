package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.order.OrderRequest;
import com.menglang.Clothing.shop.services.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> createPurchase(@PathVariable("id") Long id ) throws Exception{
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody OrderRequest order) throws Exception{
        log.info("invoke crate order..........");
        return ResponseEntity.ok(orderService.makeOrder(order));

    }
}
