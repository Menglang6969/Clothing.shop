package com.menglang.Clothing.shop.services.reports;

import com.menglang.Clothing.shop.dto.branch.BranchMapper;
import com.menglang.Clothing.shop.dto.report.ReportResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import com.menglang.Clothing.shop.repositories.BranchRepository;
import com.menglang.Clothing.shop.repositories.ExpenseIncomeRepository;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.repositories.PaymentRepository;
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
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;


    @Autowired
    public ReportServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository, ExpenseIncomeRepository expenseIncomeRepository, BranchRepository branchRepository, BranchMapper branchMapper1) {
        this.orderRepository = orderRepository;
        this.expenseIncomeRepository = expenseIncomeRepository;
        this.paymentRepository = paymentRepository;
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper1;
    }

    @Override
    public ReportResponse getReportByDate(String branch_id, String dateFrom, String dateTo) throws Exception {
        Date startDate = parseDate(dateFrom, "yyyy/MM/dd");
        Date endDate = parseDate(dateTo + " 23:59:59", "yyyy/MM/dd HH:mm:ss");

        BranchEntity branch = getBranch(branch_id);

        log.info("branch data {}", branch != null ? branch.getName() : null);

        double totalBaseCost = Optional.ofNullable(orderRepository.sumTotalBaseCostBetweenDates(branch, startDate, endDate)).orElse(0.0);
        double totalPriceUSD = Optional.ofNullable(paymentRepository.sumTotalPriceUSDBetweenDates(branch, startDate, endDate)).orElse(0.0);
        double totalPriceKHR = Optional.ofNullable(paymentRepository.sumTotalPriceKHRBetweenDates(branch, startDate, endDate)).orElse(0.0);
        double totalDebt = Optional.ofNullable(paymentRepository.getTotalDebtBetweenDate(branch, startDate, endDate)).orElse(0.0);
        double totalExpense = Optional.ofNullable(expenseIncomeRepository.getTotalExpenseIncomeByDate(branch, ExpenseIncomeType.EXPENSE, startDate, endDate)).orElse(0.0);
        double otherIncome = Optional.ofNullable(expenseIncomeRepository.getTotalExpenseIncomeByDate(branch, ExpenseIncomeType.INCOME, startDate, endDate)).orElse(0.0);
        double totalSellUSD=Optional.ofNullable(orderRepository.sumTotalPriceUSDBetweenDates(branch, startDate, endDate)).orElse(0.0);
        log.info("Base cost: {} | Total Price USD: {} | Total Price KHR: {} | totalExpense:{}", totalBaseCost, totalPriceUSD, totalPriceKHR, totalExpense);

        return ReportResponse.builder()
                .startDate(dateFrom)
                .endDate(dateTo)
                .totalReceiveKHR(totalPriceKHR)
                .totalReceiveUSD(totalPriceUSD)
                .totalSellUSD(totalSellUSD)
                .branch(branchMapper.toDTO(branch))
                .totalIncome(otherIncome + totalPriceUSD + totalDebt + (totalPriceKHR / 4000) - totalBaseCost)
                .totalDebt(totalDebt)
                .totalExpense(totalExpense)
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

    // Helper method to get BranchEntity based on branch ID
    private BranchEntity getBranch(String branchId) {
        if (!"all".equals(branchId)) {
            try {
                Long branchIdLong = Long.valueOf(branchId);
                return branchRepository.findById(branchIdLong).orElse(null);
            } catch (NumberFormatException e) {
                log.warn("Invalid branch ID format: {}", branchId);
            }
        }
        return null;
    }
}
