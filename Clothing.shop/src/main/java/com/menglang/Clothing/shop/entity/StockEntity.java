package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "stocks")
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockEntity extends BaseAuditEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchEntity branch;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private SizeEntity size;

    // The color of the product in this stock entry
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @Column(nullable = false)
    private Integer quantity;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StockEntity that = (StockEntity) object;
        return Objects.equals(product, that.product) && Objects.equals(branch, that.branch) && Objects.equals(size, that.size) && Objects.equals(color, that.color) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, branch, size, color, quantity);
    }


}
