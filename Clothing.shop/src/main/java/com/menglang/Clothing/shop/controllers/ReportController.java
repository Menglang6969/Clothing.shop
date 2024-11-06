package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.report.ReportResponse;
import com.menglang.Clothing.shop.services.reports.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reports")
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    private final ReportServiceImpl reportService;


    @GetMapping
    public ResponseEntity<ReportResponse> getReportsByDate(
            @RequestParam(name = "start_date") String start_date,
            @RequestParam(name = "end_date") String end_date
    ) throws Exception {
        log.info("invoke report controller..............");
        return ResponseEntity.ok(reportService.getReportByDate(start_date, end_date));
    }
}
