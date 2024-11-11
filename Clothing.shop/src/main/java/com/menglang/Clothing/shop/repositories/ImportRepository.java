package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ExportEntity;
import com.menglang.Clothing.shop.entity.ImportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<ImportEntity,Long> {
    @Query("select i from ImportEntity i where upper(i.importNo) like upper(concat('%', ?1, '%'))")
    Page<ImportEntity> findAllByImportNoContainingIgnoreCase(String query, Pageable pageable);
}
