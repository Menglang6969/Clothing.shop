package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "export_details")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExportDetailsEntity extends BaseEntity<Long> implements Serializable {
    // private static final long serialVersionUID = 34L;
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

    // Rename the field to avoid conflicts with 'import' keyword
    @ManyToOne
    @JoinColumn(name = "export_id",nullable = false)
    private ExportEntity exportRecord;



}
