package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import com.menglang.Clothing.shop.entity.enums.ExpenseIncomeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ExpenseIncomeRepository extends JpaRepository<ExpenseIncomeEntity,Long> {
    @Query("select e from ExpenseIncomeEntity e where e.type = ?1")
    Page<ExpenseIncomeEntity> findAllByType(ExpenseIncomeType type, Pageable pageable);

    @Query("SELECT SUM(e.amount) from ExpenseIncomeEntity e WHERE (?1 IS NULL OR e.branch = ?1) AND e.type=?2 AND  e.createdAt BETWEEN ?3 AND ?4" )
    Double getTotalExpenseIncomeByDate(BranchEntity branch, ExpenseIncomeType type, Date startDate,Date endDate);
}
