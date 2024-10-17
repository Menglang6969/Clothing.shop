package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ImportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<ImportEntity,Long> {
}
