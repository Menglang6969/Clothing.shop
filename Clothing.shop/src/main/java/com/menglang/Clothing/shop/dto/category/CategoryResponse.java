package com.menglang.Clothing.shop.dto.category;

import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class CategoryResponse extends BaseResponseAudit {
    Long id;
    private String  name;
    String description;
    CategoryDTO parent;


    public CategoryResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}
