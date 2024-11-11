package com.menglang.Clothing.shop.dto.expenseIncome;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import lombok.Builder;


@Builder
public record ExpenseIncomeRequest(
        @JsonProperty("expense_income_type")
        ExpenseIncomeType expenseIncomeType,

        @JsonProperty("expense_income_on")
        String expenseIncomeOn,

        double amount,
        String description,
        Long branch
) {

}
