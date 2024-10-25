package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_items")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id",nullable = false)
    private PurchaseOrderEntity purchase;

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
