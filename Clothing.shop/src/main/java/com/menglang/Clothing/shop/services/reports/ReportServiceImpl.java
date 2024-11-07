package com.menglang.Clothing.shop.services.reports;

import com.menglang.Clothing.shop.dto.report.ReportResponse;
import com.menglang.Clothing.shop.exceptions.InternalServerErrorException;
import com.menglang.Clothing.shop.repositories.ExpenseIncomeRepository;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.repositories.PaymentRepository;
import com.menglang.Clothing.shop.utils.DateFormat;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ExpenseIncomeRepository expenseIncomeRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public ReportServiceImpl(OrderRepository orderRepository,PaymentRepository paymentRepository, ExpenseIncomeRepository expenseIncomeRepository) {
        this.orderRepository = orderRepository;
        this.expenseIncomeRepository = expenseIncomeRepository;
        this.paymentRepository=paymentRepository;
    }

    @Override
    public ReportResponse getReportByDate(String dateFrom, String dateTo) {
        Date startDate = parseDate(dateFrom, "yyyy/MM/dd");
        Date endDate = parseDate(dateTo + " 23:59:59", "yyyy/MM/dd HH:mm:ss");

        double totalBaseCost = Optional.ofNullable(orderRepository.sumTotalBaseCostBetweenDates(startDate, endDate)).orElse(0.0);
        double totalPriceUSD = Optional.ofNullable(orderRepository.sumTotalPriceBetweenDates(startDate, endDate)).orElse(0.0);
        double totalPriceKHR = Optional.ofNullable(orderRepository.sumTotalPriceKHRBetweenDates(startDate, endDate)).orElse(0.0);
        double totalDebt=Optional.ofNullable(paymentRepository.getTotalDebtBetweenDate())
        log.info("Base cost: {} | Total Price USD: {} | Total Price KHR: {}", totalBaseCost, totalPriceUSD, totalPriceKHR);

        return ReportResponse.builder()
                .startDate(dateFrom)
                .endDate(dateTo)
                .totalSellKHR(totalPriceKHR)
                .totalSellUSD(totalPriceUSD)
                .totalIncome(totalPriceUSD - totalBaseCost)
                .build();
    }

    private Date parseDate(String dateString, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (Exception e) {
            log.error("Error parsing date: {}", dateString, e);
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }
}
