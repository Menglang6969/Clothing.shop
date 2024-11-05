package com.menglang.Clothing.shop.dto.stock;

import com.menglang.Clothing.shop.dto.stock.productStock.ProductDetails;
import com.menglang.Clothing.shop.entity.StockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class StockDTO {

    private static final Logger log = LoggerFactory.getLogger(StockDTO.class);

    public List<StockResponse> mapToStockDTO(List<StockEntity> data) {
        Map<Long, StockResponse> stockResponseMap = new HashMap<>();

        for (StockEntity item : data) {
            Long product_id = item.getProduct().getId();
            String product_name = item.getProduct().getTitle();
            String size = item.getSize().getName();
            String color = item.getColor().getName();
            Long branch = item.getBranch().getId();
            int quantity = item.getQuantity();

            ProductDetails productDetails = ProductDetails.builder()
                    .branch_id(branch)
                    .color(color)
                    .quantity(quantity)
                    .size(size)
                    .build();

            stockResponseMap.putIfAbsent(product_id,new StockResponse(product_id,product_name,new ArrayList<>()));
            log.info("product details: {} size: {} color:{} quantity:{}", product_id, size, color, quantity);

            stockResponseMap.get(product_id).getProductDetails().add(productDetails);
            log.info("Stock product details: {} ", stockResponseMap.get(product_id));
        }
        return new ArrayList<>(stockResponseMap.values());

    }

    private StockEntity checkExistProduct(List<StockEntity> data, Long id) {
        return (StockEntity) data.stream().filter(p -> p.getProduct().getId().equals(id));
    }


}
