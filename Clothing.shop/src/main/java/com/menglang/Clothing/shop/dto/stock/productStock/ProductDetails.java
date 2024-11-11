package com.menglang.Clothing.shop.dto.stock.productStock;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class ProductDetails {
    private Long branch_id;
    private String size;
    private String color;
    private int quantity;

}
