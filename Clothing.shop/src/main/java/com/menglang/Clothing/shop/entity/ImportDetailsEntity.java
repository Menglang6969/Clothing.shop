package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(name = "import_details")
@Builder
@Setter
@Getter
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
    @JoinColumn(name = "import_record")
    private ImportEntity import_record;

}
