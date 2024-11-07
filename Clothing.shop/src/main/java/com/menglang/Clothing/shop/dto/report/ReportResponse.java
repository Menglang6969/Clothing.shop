package com.menglang.Clothing.shop.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReportResponse(
        String startDate,
        String endDate,
        BranchDTO branch,
        double totalSellUSD,
        double totalSellKHR,
        double totalIncome,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        double totalExpense,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        double totalDebt

) {
}
