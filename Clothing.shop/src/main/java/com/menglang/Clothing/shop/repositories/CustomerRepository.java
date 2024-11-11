package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    @Query("select c from CustomerEntity c where upper(c.name) like upper(concat('%', ?1, '%'))")
    Page<CustomerEntity> findAllByNameContainingIgnoreCase(String query, Pageable pageable);
}
