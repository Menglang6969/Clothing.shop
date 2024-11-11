package com.menglang.Clothing.shop.dto.payment;
import lombok.Builder;

@Builder
public record MoneyChecker(
        Double paymentUSD,
        Double paymentKHR,
        Double returnMoney
) {
}
