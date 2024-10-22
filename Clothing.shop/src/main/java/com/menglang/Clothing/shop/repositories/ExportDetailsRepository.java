package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ExportDetailsEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportDetailsRepository extends JpaRepository<ExportDetailsEntity,Long> {
}
