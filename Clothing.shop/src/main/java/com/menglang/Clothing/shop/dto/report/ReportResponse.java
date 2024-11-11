package com.menglang.Clothing.shop.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.Clothing.shop.dto.branch.BranchResponse;
import lombok.Builder;

@Builder
public record ReportResponse(
        String startDate,
        String endDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        BranchResponse branch,
        double totalSellUSD,
        double totalReceiveUSD,
        double totalReceiveKHR,
        double totalIncome,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        double totalExpense,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        double totalDebt

) {
}
