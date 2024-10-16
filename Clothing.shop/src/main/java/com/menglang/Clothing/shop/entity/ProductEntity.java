package com.menglang.Clothing.shop.entity;
import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_tbl")
public class ProductEntity extends BaseAuditEntity<Long> {


    @Column(length = 50)
    private String title;

    @Column(name = "base_cost", precision = 10)
    private BigDecimal baseCost;//calculate base Average cost

    @Column(name = "sell_cost", precision = 10)
    private BigDecimal sellCost;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<StockEntity> stocks=new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "color_id",referencedColumnName = "id")
    )
    @Builder.Default
    private Set<ColorEntity> colors=new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "product_sizes",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    @Builder.Default
    private Set<SizeEntity> sizes=new HashSet<>();


    @Column( length = 150)
    private String imageUrl;

    @Column(length = 100)
    private String description;

    @Column(length = 10,name = "discounted_price")
    private Integer discountedPrice;


    @Column(length = 10,name = "discounted_percent")
    private Integer discountedPercent;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RatingEntity> ratings = new HashSet<>();

    @Column(name = "num_rating")
    private int numRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private CategoryEntity category;
}
