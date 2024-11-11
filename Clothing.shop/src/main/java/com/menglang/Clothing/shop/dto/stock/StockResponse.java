package com.menglang.Clothing.shop.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import com.menglang.Clothing.shop.dto.stock.productStock.ProductDetails;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse extends BasePageResponse {
    private Long id;
    private String name;
    private List<ProductDetails> productDetails;

    public void addProductDetails(ProductDetails data){
        this.productDetails.add(data);
    }
}
