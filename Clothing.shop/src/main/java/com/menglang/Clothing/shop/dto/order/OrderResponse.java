package com.menglang.Clothing.shop.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.customer.CustomerDTO;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsResponse;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Setter
@Getter

public class OrderResponse extends BaseResponseAudit {
    @JsonProperty(index = 1)
    Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 2)
    String generalCustomer;

    @JsonProperty(index = 3)
    CustomerType customerType;


    @JsonProperty(index = 4)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    CustomerDTO customer;

    @JsonProperty(index = 5)
    double discountedPrice;

    @JsonProperty(index = 6)
    int discountedPercent;

    @JsonProperty(index = 7)
    BranchDTO branch;

    @JsonProperty(index = 8)
    int totalItem;

    @JsonProperty(index = 8)
    double totalPrice;

    @JsonProperty(index = 9)
    List<OrderDetailsResponse> orderItems;

    String address;

    public OrderResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}