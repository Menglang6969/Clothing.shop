package com.menglang.Clothing.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Setter
@Entity
@Table(name = "order_items_tbl")
public class OrderItemsEntity extends BaseEntity<Long> {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    private ProductEntity product;

    @Column(length = 10)
    private String size;

    private int quantity;

    private Integer price;

    @Column(name = "discounted_price")
    private Integer discountedPrice;


}
