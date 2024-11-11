package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "colors")
public class ColorEntity extends BaseAuditEntity<Long> {

    @Column(length = 40)
    private String name;

    @ManyToMany(mappedBy = "colors")
    @Builder.Default
    private Set<ProductEntity> products=new HashSet<>();
}
