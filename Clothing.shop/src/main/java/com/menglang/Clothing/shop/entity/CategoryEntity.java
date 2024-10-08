package com.menglang.Clothing.shop.entity;


import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_tbl")
public class CategoryEntity extends BaseAuditEntity<Long> {

    @Column(unique=true,length=30)
    private String name;

    @ManyToOne()
    @JoinColumn(
            name = "parent_category_id"
    )
    private CategoryEntity parent;

    private int level;

    @Column(unique=true,length=50)
    private String description;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<ProductEntity> product=new HashSet<>();

}
