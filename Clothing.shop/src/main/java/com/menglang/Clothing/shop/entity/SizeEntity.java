package com.menglang.Clothing.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sizes")
public class SizeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;// Example: S, M, L, XL, etc.

    @ManyToMany(mappedBy = "sizes")
    @Builder.Default
    private Set<ProductEntity> products=new HashSet<>();
}
