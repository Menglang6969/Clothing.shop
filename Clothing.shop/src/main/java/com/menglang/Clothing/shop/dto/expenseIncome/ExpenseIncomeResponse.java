package com.menglang.Clothing.shop.dto.expenseIncome;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.dto.BaseResponseAudit;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ExpenseIncomeResponse extends BaseResponseAudit {
    @JsonProperty(index = 1)
    Long id;

    @JsonProperty(index = 2)
    ExpenseIncomeType expenseIncomeType;

    @JsonProperty(index = 3)
    String expenseIncomeOn;

    @JsonProperty(index = 4)
    double amount;

    @JsonProperty(index = 5)
    String description;

    public ExpenseIncomeResponse(String createdBy, String updatedBy, Date createdAt, Date updatedAt) {
        super(createdBy, updatedBy, createdAt, updatedAt);
    }
}
