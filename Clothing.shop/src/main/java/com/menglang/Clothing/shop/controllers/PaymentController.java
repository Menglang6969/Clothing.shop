package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.dto.payment.PaymentRequest;
import com.menglang.Clothing.shop.services.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/payments")
@RequiredArgsConstructor
public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponseTemplate> makePayment(@RequestBody PaymentRequest payment){
        ResponseTemplate res=ResponseTemplate.builder()
                .object(paymentService.makePayment(payment))
                .code("201")
                .message("successful")
                .build();
        return ResponseEntity.ok(res);
    }
}
