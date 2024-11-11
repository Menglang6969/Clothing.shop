package com.menglang.Clothing.shop.entity.base;

import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseItemDetails extends BaseAuditEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "size_id",nullable = false)
    private SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "color_id",nullable = false)
    private ColorEntity color;

    @Column(nullable = false)
    private int quantity;

    private Double price;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    @Column(name = "discounted_percent")
    private int discountedPercent;
}
