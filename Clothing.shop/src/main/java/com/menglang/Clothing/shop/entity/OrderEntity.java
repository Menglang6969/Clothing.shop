package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_tbl",
        indexes = @Index(name = "id_order_no", columnList = "order_no")
)
public class OrderEntity extends BaseAuditEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;

    @Column(name = "order_no",length = 20,unique = true)
    private String orderNo;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_price_khr")
    private double totalPriceKHR;

    @Column(name = "total_item")
    private int totalItem;

    @Column(name = "total_discounted_price")
    private double discountedPrice;

    @Column(name = "total_discounted_percent")
    private int discountedPercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_type")
    private CustomerType customerType;

    @Column(name = "generalCustomer")
    private String generalCustomer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemsEntity> orderItems;

    @Column(name = "total_base_price")
    Double totalBasePrice;

    private String address;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
