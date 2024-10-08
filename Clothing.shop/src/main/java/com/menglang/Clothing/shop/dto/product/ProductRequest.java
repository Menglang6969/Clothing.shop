package com.menglang.Clothing.shop.dto.product;


import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class ProductRequest {

    private String title;
    private String description;
    private double price;
    private String brand;
    private Long categoryId;

    private  int discountPrice;
    private int discountPercentage;
    private int quantity;
    private  String color;
    private String imageUrl;

    private Set<Size> sizes=new HashSet<>();
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;

    //private String category;

}
