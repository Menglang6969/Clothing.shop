package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseIncomeRepository extends JpaRepository<ExpenseIncomeEntity,Long> {
    @Query("select e from ExpenseIncomeEntity e where e.type = ?1")
    Page<ExpenseIncomeEntity> findAllByType(ExpenseIncomeType type, Pageable pageable);
}
