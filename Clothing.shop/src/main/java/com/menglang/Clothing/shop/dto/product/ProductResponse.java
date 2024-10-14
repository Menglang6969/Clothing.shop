package com.menglang.Clothing.shop.dto.product;

import com.menglang.Clothing.shop.dto.category.CategoryDTO;
import com.menglang.Clothing.shop.dto.colors.ColorDTO;
import com.menglang.Clothing.shop.dto.size.SizeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ProductResponse {

    private Long id;
    private String title;
    private String description;
    private double sellCost;
    private double baseCost;
    private CategoryDTO category;
    private int discountedPrice;
    private int discountedPercent;
    private String imageUrl;
    private List<ColorDTO> colors;
    private List<SizeDTO> sizes;

    // Auditing fields
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
