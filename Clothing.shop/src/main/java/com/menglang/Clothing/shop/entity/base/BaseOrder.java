package com.menglang.Clothing.shop.entity.base;

import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseOrder extends BaseAuditEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "branch_id",nullable = false)
    private BranchEntity branch;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_item")
    private int totalItem;

    @Column(name = "total_discounted_price")
    private double totalDiscountedPrice;

    @Column(name = "total_discounted_percent")
    private int totalDiscountedPercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @Column(name = "generalCustomer")
    private String generalCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

}
