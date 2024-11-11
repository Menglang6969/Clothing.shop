package com.menglang.Clothing.shop.services.reports;

import com.menglang.Clothing.shop.dto.report.ReportResponse;

import java.util.Date;

public interface ReportService {
    public ReportResponse getReportByDate(String branch_id, String startDate,String endDate)throws Exception;
}
