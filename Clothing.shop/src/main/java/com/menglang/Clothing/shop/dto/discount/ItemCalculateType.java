package com.menglang.Clothing.shop.dto.discount;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class ItemCalculateType implements Serializable {
    Double price;
    int quantity;
    Double discountedPrice;
    int discountedPercent;
}
