package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findFirstByName(String name);
    @Query("select r from RoleEntity r where r.name in ?1")
    List<RoleEntity> findAllByNameIn(List<String> names);

}
