package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@Table(name = "order_tbl")
public class OrderEntity extends BaseAuditEntity<Long> {

    @Column(name = "shipping_address")
    @OneToOne
    AddressEntity shippingAddress;
    @Column(name = "order_id")
    private String orderId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivered_at")
    private Date deliveredAt;


    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_discounted_price")
    private Integer totalDiscountedPrice;

    private Integer discount;

    @Column(name = "order")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalItems;

    @ManyToOne()
    private UserEntity user;

    @ManyToOne()
    private CustomerEntity customer;

    @Embedded
    private PaymentDetails paymentDetails;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemsEntity> orderItems;
}
