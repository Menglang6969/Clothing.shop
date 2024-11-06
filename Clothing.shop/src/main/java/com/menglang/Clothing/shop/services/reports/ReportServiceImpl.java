package com.menglang.Clothing.shop.services.reports;

import com.menglang.Clothing.shop.dto.report.ReportResponse;
import com.menglang.Clothing.shop.exceptions.InternalServerErrorException;
import com.menglang.Clothing.shop.repositories.ExpenseIncomeRepository;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.utils.DateFormat;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ExpenseIncomeRepository expenseIncomeRepository;

    @Override
    public ReportResponse getReportByDate(String dateFrom, String dateTo) throws Exception {
        Date startDate = new SimpleDateFormat("yyyy/MM/dd").parse(String.valueOf(dateFrom));
        DateFormat endDate_ = new DateFormat(dateTo);
        Date endDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(endDate_ + " 23:59:59");

       try{
           double totalBaseCost = orderRepository.sumTotalBaseCostBetweenDates(startDate, endDate);
           double totalPriceUSD = orderRepository.sumTotalPriceBetweenDates(startDate, endDate);
           double totalPriceKHR = orderRepository.sumTotalPriceKHRBetweenDates(startDate, endDate);

           log.info("base cost: {} totalPriceUSD: {} totalPriceKHR:{}",totalBaseCost,totalPriceUSD,totalPriceKHR);


       }catch (Exception e){
           throw new InternalServerErrorException(e.getMessage());
       }


        return null;
    }
}
