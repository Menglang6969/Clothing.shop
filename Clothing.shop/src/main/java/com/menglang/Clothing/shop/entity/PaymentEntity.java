package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments",
        indexes = @Index(name = "customer_name", columnList = "customer")
)
public class PaymentEntity extends BaseAuditEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "order_no",nullable = false)
    private OrderEntity order;

    @Column(name = "customer")
    private String customer;

    @Column(name = "pay_usd")
    private Double payUSD;
    @Column(name = "pay_khr")
    private Double payKHR;

    @Column(name = "debt_usd")
    private Double debtUSD;

    @Column(name = "debt")
    private Double debt;

    private String description;



}
