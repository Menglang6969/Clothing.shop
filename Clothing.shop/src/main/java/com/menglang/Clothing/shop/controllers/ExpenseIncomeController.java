package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerResponse;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeMapper;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeRequest;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import com.menglang.Clothing.shop.services.expenseIncome.ExpenseIncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/expense_income")
@RequiredArgsConstructor
public class ExpenseIncomeController {

    private final ExpenseIncomeService expenseIncomeService;
    private final ExpenseIncomeMapper expenseIncomeMapper;

    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody ExpenseIncomeRequest data) throws Exception {
        return ResponseEntity.ok(expenseIncomeService.create(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> update(@PathVariable("id") Long id, @RequestBody ExpenseIncomeRequest data) throws Exception {
        return ResponseEntity.ok(expenseIncomeService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> delete(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(expenseIncomeService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> get(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(expenseIncomeService.view(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "desc:createdAt",required = false) String sortBy,
            @RequestParam(value = "type",defaultValue = "INCOME",required = false) ExpenseIncomeType type
    ) throws Exception {
        Page<ExpenseIncomeEntity> expenseIncomeEntities=expenseIncomeService.findAll(page,limit,sortBy,type);
        List<ExpenseIncomeResponse> expenseIncomeResponses=expenseIncomeEntities.stream().map(this.expenseIncomeMapper::toExpenseIncomeDTO).toList();
        return BaseResponse.success( expenseIncomeResponses,expenseIncomeEntities, "success");
    }


}
