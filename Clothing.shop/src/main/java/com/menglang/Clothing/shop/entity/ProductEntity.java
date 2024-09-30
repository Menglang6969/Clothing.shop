package com.menglang.Clothing.shop.entity;
import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import jakarta.persistence.*;
import lombok.*;

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


    @Column(unique = true, length = 50)
    private String title;

    @Column()
    private Double price;

    @Column(unique = true, length = 50)
    private String brand;

    @Column(unique = true, length = 50)
    private String color;

    @Column()
    private Integer quantity;

    @ElementCollection
    @CollectionTable(name = "size",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    private Set<Size> sizes=new HashSet<>();

    @Column(unique = true, length = 150)
    private String imageUrl;


    @Column(length = 100,unique=true)
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
    private CategoryEntity category;
}
