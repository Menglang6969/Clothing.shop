package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Long> {

    @Query("select f from FileEntity f where f.name = ?1 and f.deletedAt is null")
    Page<FileEntity> findAllByNameAndDeletedAtIsNull(String fileName, Pageable pageable);

    @Query("select f from FileEntity f where f.id = ?1 and f.deletedAt is not null")
    FileEntity findByIdAndDeletedAtIsNotNull(Long id);

    @Query("""
            select f from FileEntity f
            where upper(f.originalName) like upper(concat('%', ?1, '%')) and f.deletedAt is null""")
    Page<FileEntity> findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNull(String query,Pageable pageable);
    


    @Query("""
            select f from FileEntity f
            where upper(f.originalName) like upper(concat('%', ?1, '%')) and f.deletedAt is not null""")
    Page<FileEntity> findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNotNull(String query, Pageable pageable);

}
