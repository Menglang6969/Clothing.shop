package com.menglang.Clothing.shop.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.customer.CustomerDTO;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsResponse;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.entity.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter

public class OrderResponse extends BaseResponseAudit {
    @JsonProperty(index = 1)
    Long id;
    @JsonProperty(index = 2)
    private String orderNo;;
    @JsonProperty(index = 8)
    BranchDTO branch;
    @JsonProperty(index = 5)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    CustomerDTO customer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(index = 3)
    String generalCustomer;
    @JsonProperty(index = 4)
    CustomerType customerType;
    @JsonProperty(index = 9)
    int totalItem;

    @JsonProperty(index = 6)
    double discountedPrice;

    @JsonProperty(index = 7)
    int discountedPercent;

    @JsonProperty(index = 10)
    double totalPrice;

    @JsonProperty(index = 11)
    double totalPriceKHR;

    @JsonProperty(index = 12)
    PaymentStatus status;

    @JsonProperty(index = 13)
    String address;
    @JsonProperty(index = 14)
    List<OrderDetailsResponse> orderItems;

    public OrderResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}