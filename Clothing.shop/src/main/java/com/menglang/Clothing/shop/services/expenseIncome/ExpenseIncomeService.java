package com.menglang.Clothing.shop.services.expenseIncome;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeRequest;
import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import org.springframework.data.domain.Page;

public interface ExpenseIncomeService {
    public ResponseTemplate create(ExpenseIncomeRequest data) throws Exception;
    public ResponseTemplate view(Long id) throws Exception;
    public ResponseTemplate update(Long id,ExpenseIncomeRequest data) throws Exception;
    public ResponseTemplate delete(Long id) throws Exception;
    public Page<ExpenseIncomeEntity> findAll(int page, int limit, String sort, ExpenseIncomeType type) throws Exception;

}
