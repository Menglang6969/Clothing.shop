package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.HashSet;
import java.util.Set;
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "branches")
public class BranchEntity extends BaseAuditEntity<Long> {

    @Column(nullable = false)
    private String name;

    private String address;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<StockEntity> stocks = new HashSet<>();


}
