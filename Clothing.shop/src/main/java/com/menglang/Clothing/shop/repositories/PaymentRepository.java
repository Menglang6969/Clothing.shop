package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p "+
        "WHERE p.order=?1 AND p.debt > 0 ")
    public List<PaymentEntity> findPreviousPayment(OrderEntity order,Pageable pageable);

    @Query("SELECT p FROM PaymentEntity p " +
            "JOIN p.order o " +
            "WHERE p.customer=?2 OR (?1 IS NULL OR p.order=?1)")
    public Page<PaymentEntity> findPaymentByOrderORByCustomer(OrderEntity order, String customer, Pageable pageable);

    @Query("Select Sum(p.debt) from PaymentEntity p where (?1 IS NULL OR p.branch = ?1) AND p.createdAt BETWEEN ?2 AND ?3")
    public Double getTotalDebtBetweenDate(BranchEntity branch, Date startDate, Date endDate);

    @Query("SELECT SUM(p.payKHR) FROM PaymentEntity p WHERE (?1 IS NULL OR p.branch = ?1) AND p.createdAt BETWEEN ?2 AND ?3")
    Double sumTotalPriceKHRBetweenDates(BranchEntity branch,Date startDate, Date endDate);

    @Query("SELECT SUM(p.payUSD) FROM PaymentEntity p WHERE  (?1 IS NULL OR p.branch = ?1) AND p.createdAt BETWEEN ?2 AND ?3")
    Double sumTotalPriceUSDBetweenDates(BranchEntity branch,Date startDate, Date endDate);
}
