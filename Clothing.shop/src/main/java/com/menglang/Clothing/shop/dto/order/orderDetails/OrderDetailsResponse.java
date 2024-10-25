package com.menglang.Clothing.shop.dto.order.orderDetails;

import com.menglang.Clothing.shop.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderDetailsResponse {
    ProductDTO product;
    String size;
    String color;
    Integer quantity;
    double price;
    double discountedPrice;
    int discountedPercent;
}
