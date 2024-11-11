package com.menglang.Clothing.shop.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CustomerResponse extends BaseResponseAudit {
    @JsonProperty(index = 1)
    Long id;
    @JsonProperty(index = 2)
    public String name;
    @JsonProperty(index = 3)
    public String phone;
    @JsonProperty(index = 4)
    public String address;

    public CustomerResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}
