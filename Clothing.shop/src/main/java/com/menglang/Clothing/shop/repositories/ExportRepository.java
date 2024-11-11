package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ExportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExportRepository extends JpaRepository<ExportEntity,Long> {

    @Query("select e from ExportEntity e where upper(e.exportNo) like upper(concat('%', ?1, '%'))")
    Page<ExportEntity> findAllByExportNoContainingIgnoreCase(String query, Pageable pageable);
}
