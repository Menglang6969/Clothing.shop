package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.BranchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity,Long> {

    @Query("select b from BranchEntity b where upper(b.name) like upper(concat('%', ?1, '%'))")
    Page<BranchEntity> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
