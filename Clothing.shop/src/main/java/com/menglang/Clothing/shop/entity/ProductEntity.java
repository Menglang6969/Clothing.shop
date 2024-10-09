package com.menglang.Clothing.shop.entity;
import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import com.menglang.Clothing.shop.entity.enums.Sizes;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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

    @Column()
    private Double price;

    @Column( length = 50)
    private String brand;


    @ManyToMany
    @JoinTable(
            name = "product_colors",
            joinColumns = @JoinColumn(name = "product_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "color_id",referencedColumnName = "id")
    )
    @Builder.Default
    private Set<ColorEntity> colors=new HashSet<>();

    @Column()
    private Integer quantity;


    @ElementCollection(targetClass = Sizes.class)
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "sizes")
    @Enumerated(EnumType.STRING) // Store size as a string
    private List<Sizes> sizes;


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
    private CategoryEntity category;
}
