package com.menglang.Clothing.shop.entity;

import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_income")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseIncomeEntity extends BaseAuditEntity<Long> {

    @Enumerated(EnumType.STRING)
    private ExpenseIncomeType type;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @Column(name = "expense_income_on")
    private String expenseIncomeOn;

    private Double amount;

    private String description;

}
