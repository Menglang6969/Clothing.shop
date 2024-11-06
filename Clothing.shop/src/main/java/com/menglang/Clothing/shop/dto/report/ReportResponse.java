package com.menglang.Clothing.shop.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.Clothing.shop.dto.branch.BranchDTO;

import java.time.LocalDate;

public record ReportResponse(
        LocalDate startDate,
        LocalDate endDate,
        BranchDTO branch,
        String user,
        double totalSellUSD,
        double totalSellKHR,
        double totalIncome,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        double totalExpense
) {
}
