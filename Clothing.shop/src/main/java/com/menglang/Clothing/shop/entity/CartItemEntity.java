package com.menglang.Clothing.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_item_tbl")
@Setter
@Getter
@Builder
public class CartItemEntity extends BaseEntity<Long> {

    @ManyToOne
    @JsonIgnore
    private CartEntity cart;

    @ManyToOne
    private ProductEntity product;

    private String size;

    private int quantity;

    private Double price;

    @Column(name = "discounted_price")
    private int discountedPrice;


}
