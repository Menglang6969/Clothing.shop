package com.menglang.Clothing.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "purchase_items")
@Setter
@Getter
@Builder
public class PurchaseItemEntity extends BaseEntity<Long> {

    @ManyToOne()
    @JoinColumn(name = "purchase_id",nullable = false)
    private PurchaseOrderEntity purchase;

    @ManyToOne
    private ProductEntity product;

    private Long size;

    private Long color;

    private int quantity;

    private Double price;

    @Column(name = "discounted_price")
    private int discountedPrice;

    @Column(name = "discounted_percent")
    private int discounted_percent;


}
