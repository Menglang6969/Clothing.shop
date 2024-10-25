package com.menglang.Clothing.shop.dto;

import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class BaseResponseAudit extends BasePageResponse {
    // Auditing fields
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
