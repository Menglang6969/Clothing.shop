package com.menglang.Clothing.shop.services.payment;

import com.menglang.Clothing.shop.dto.payment.PaymentRequest;
import com.menglang.Clothing.shop.dto.payment.PaymentResponse;
import com.menglang.Clothing.shop.exceptions.BadRequestException;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest data) throws BadRequestException;
    PaymentResponse deletePayment(Long data) throws BadRequestException;
    PaymentResponse editPayment(Long id,PaymentRequest data) throws BadRequestException;
}
