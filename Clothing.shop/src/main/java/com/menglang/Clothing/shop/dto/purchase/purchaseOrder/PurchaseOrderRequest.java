package com.menglang.Clothing.shop.dto.purchase.purchaseOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Builder
public record PurchaseOrderRequest(
        @JsonProperty("customer_name")
        String generalCustomer,
        @JsonProperty("customer_type")
        CustomerType customerType,
        Long customer,
        int discount,
        @NonNull
        List<ItemRequest> items
) {
}
