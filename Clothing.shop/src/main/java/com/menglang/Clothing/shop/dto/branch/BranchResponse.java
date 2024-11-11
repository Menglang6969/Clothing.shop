package com.menglang.Clothing.shop.dto.branch;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    Long id;
    String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String description;
    // Auditing fields
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;
}
