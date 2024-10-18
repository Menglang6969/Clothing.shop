package com.menglang.Clothing.shop.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockResponse {
    private Long id;
    private Long product;
    private Long size;
    private Long color;
    private int quantity;
    private double importCost;
}
