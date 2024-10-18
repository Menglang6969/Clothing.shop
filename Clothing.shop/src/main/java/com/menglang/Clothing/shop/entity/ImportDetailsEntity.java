package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "import_details",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id","size_id","color_id"})

)
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImportDetailsEntity extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;



    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @Column(name = "import_cost")
    private Double importCost;

    // Rename the field to avoid conflicts with 'import' keyword
    @ManyToOne
    @JoinColumn(name = "import_id",nullable = false)
    private ImportEntity importRecord;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ImportDetailsEntity that = (ImportDetailsEntity) object;
        return Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(size, that.size) && Objects.equals(color, that.color) && Objects.equals(importCost, that.importCost) && Objects.equals(importRecord, that.importRecord);
    }


    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, size, color, importCost, importRecord);
    }

}
