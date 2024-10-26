package com.menglang.Clothing.shop.services.expenseIncome;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeRequest;

public interface ExpenseIncomeService {
    public ResponseTemplate create(ExpenseIncomeRequest data) throws Exception;
    public ResponseTemplate view(Long id) throws Exception;
    public ResponseTemplate update(Long id,ExpenseIncomeRequest data) throws Exception;
    public ResponseTemplate delete(Long id) throws Exception;

}
