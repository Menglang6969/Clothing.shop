package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity,Long> {
}
