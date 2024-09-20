package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@Table(name = "cart_tbl")
public class CartEntity extends BaseAuditEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "cart_items")
    private Set<CartItemEntity> cartItems = new HashSet<>();

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_item")
    private int totalItem;

    @Column(name = "total_discounted_price")
    private int totalDiscountedPrice;

    private int discount;
}
