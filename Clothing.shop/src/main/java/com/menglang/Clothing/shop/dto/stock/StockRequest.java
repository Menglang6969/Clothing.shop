package com.menglang.Clothing.shop.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
public class StockRequest {
    private Long product;
    private Long size;
    private Long color;
    private int quantity;

    @JsonProperty("import_cost")
    private double importCost;

}
