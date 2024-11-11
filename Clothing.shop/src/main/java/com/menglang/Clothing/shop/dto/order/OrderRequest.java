package com.menglang.Clothing.shop.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import jakarta.persistence.JoinColumn;
import lombok.*;

import java.util.List;

@Builder

public record OrderRequest (
        @JsonProperty("order_no")
        String orderNo,
        @JsonProperty("customer_name")
        String generalCustomer,
        @JsonProperty("customer_type")
        CustomerType customerType,
        Long customer,
        Long branch,
        @JsonProperty("discounted_price")
        double discountedPrice,
        @JsonProperty("discounted_percent")
        int discountedPercent,
        @NonNull
        List<OrderDetailsRequest> items,
        String address
) {
}
