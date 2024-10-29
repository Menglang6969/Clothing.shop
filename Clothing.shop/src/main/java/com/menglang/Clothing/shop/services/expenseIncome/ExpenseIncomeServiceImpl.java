package com.menglang.Clothing.shop.services.expenseIncome;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeMapper;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeRequest;
import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.ExpenseIncomeRepository;
import com.menglang.Clothing.shop.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseIncomeServiceImpl implements ExpenseIncomeService {
    private final ExpenseIncomeRepository expenseIncomeRepository;
    private final ExpenseIncomeMapper expenseIncomeMapper;

    @Override
    public ResponseTemplate create(ExpenseIncomeRequest data) throws Exception {
        try {
            ExpenseIncomeEntity expenseIncome = ExpenseIncomeEntity.builder()
                    .expenseIncomeOn(data.expenseIncomeOn())
                    .amount(data.amount())
                    .description(data.description())
                    .type(data.expenseIncomeType())
                    .build();
            ExpenseIncomeEntity savedExpenseIncome = expenseIncomeRepository.save(expenseIncome);
            return ResponseTemplate.builder()
                    .message("create successful")
                    .code("201")
                    .object(expenseIncomeMapper.toExpenseIncomeDTO(savedExpenseIncome))
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public ResponseTemplate view(Long id) throws Exception {
        ExpenseIncomeEntity expenseIncome = this.findById(id);
        return ResponseTemplate.builder()
                .object(expenseIncomeMapper.toExpenseIncomeDTO(expenseIncome))
                .code("200")
                .message("successful get data")
                .build();
    }

    @Override
    public ResponseTemplate update(Long id, ExpenseIncomeRequest data) throws Exception {
        try {
            ExpenseIncomeEntity expenseIncome = this.findById(id);
            expenseIncome.setExpenseIncomeOn(data.expenseIncomeOn());
            expenseIncome.setDescription(data.description());
            expenseIncome.setAmount(data.amount());
            expenseIncome.setType(data.expenseIncomeType());
            ExpenseIncomeEntity expenseIncomeUpdate = expenseIncomeRepository.save(expenseIncome);

            return ResponseTemplate.builder()
                    .message("data update successful")
                    .code("200")
                    .object(expenseIncomeMapper.toExpenseIncomeDTO(expenseIncomeUpdate))
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseTemplate delete(Long id) throws Exception {
        ExpenseIncomeEntity existData = this.findById(id);
        expenseIncomeRepository.deleteById(id);
        return ResponseTemplate.builder()
                .message("data delete successful")
                .code("200")
                .object(expenseIncomeMapper.toExpenseIncomeDTO(existData))
                .build();
    }

    @Override
    public Page<ExpenseIncomeEntity> findAll(int page, int limit, String sort, ExpenseIncomeType type) throws Exception {
        Pageable pageable= PageableResponse.mapPageable(page,limit,sort);
        return expenseIncomeRepository.findAllByType(type,pageable);
    }

    private ExpenseIncomeEntity findById(Long id) throws Exception {
        return expenseIncomeRepository.findById(id).orElseThrow(() -> new NotFoundException("Data Not Found"));
    }
}
