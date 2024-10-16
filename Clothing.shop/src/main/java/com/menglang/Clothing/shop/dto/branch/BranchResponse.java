package com.menglang.Clothing.shop.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
public class BranchResponse {
    String name;
    String address;
    String description;
    // Auditing fields
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
