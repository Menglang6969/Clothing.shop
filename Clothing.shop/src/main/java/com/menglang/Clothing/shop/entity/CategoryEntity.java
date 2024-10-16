package com.menglang.Clothing.shop.entity;


import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_tbl", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")  // Ensure 'name' is unique
})
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity extends BaseAuditEntity<Long> {

    @Column(unique=true,length=30)
    private String name;

    @ManyToOne()
    @JoinColumn(
            name = "parent_id"
    )
    private CategoryEntity parentId;

    private int level;

    @Column(length=50)
    private String description;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private Set<ProductEntity> product=new HashSet<>();

}
