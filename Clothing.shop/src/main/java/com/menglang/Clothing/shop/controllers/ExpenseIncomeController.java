package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.expenseIncome.ExpenseIncomeRequest;
import com.menglang.Clothing.shop.services.expenseIncome.ExpenseIncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/expense_income")
@RequiredArgsConstructor
public class ExpenseIncomeController {

    private final ExpenseIncomeService expenseIncomeService;

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



}
