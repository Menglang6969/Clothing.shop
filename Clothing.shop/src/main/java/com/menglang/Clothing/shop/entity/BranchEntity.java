package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Set;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "branches")
public class BranchEntity extends BaseAuditEntity<Long> {

    @Column(nullable = false)
    private String name;

    private String address;

    private String description;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<StockEntity> stocks = new HashSet<>();


}
