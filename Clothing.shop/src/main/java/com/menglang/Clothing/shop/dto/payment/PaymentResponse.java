package com.menglang.Clothing.shop.dto.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.customer.CustomerDTO;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class PaymentResponse extends BaseResponseAudit {
    @JsonProperty(index = 1)
    Long id;
    @JsonProperty(index = 2)
    String orderNo;
    @JsonProperty(index = 3)
    String customer;
    @JsonProperty(index = 4)
    private Double payUSD;
    @JsonProperty(index = 5)
    private Double payKHR;
    @JsonProperty(index = 6)
    private Double debtUSD;
    @JsonProperty(index = 7)
    private Double totalDebt;
    @JsonProperty(index = 8)
    private Double returnMoney;
    @JsonProperty(index = 9)
    private String description;

    @JsonProperty(index = 9)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BranchDTO branch;
    public PaymentResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }

}
