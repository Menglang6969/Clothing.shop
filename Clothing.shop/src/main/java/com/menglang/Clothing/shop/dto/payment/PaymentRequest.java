package com.menglang.Clothing.shop.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PaymentRequest(
        @JsonProperty("order_no")
        String orderNo,
        String customer,
        @JsonProperty("pay_usd")
        double payUSD,
        @JsonProperty("pay_khr")
        double payKHR,
        String description
) {

}
