package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.entity.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@Table(name = "purchase")
@AllArgsConstructor
public class PurchaseOrderEntity extends BaseAuditEntity<Long> {

    @OneToMany(mappedBy ="purchase",cascade = CascadeType.ALL,orphanRemoval = true)
    @Column(name = "purchase_items")
    @Builder.Default
    private Set<PurchaseItemEntity> purchaseItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "branch_id",nullable = false)
    private  BranchEntity branch;

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

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    public PurchaseOrderEntity() {

    }
}
