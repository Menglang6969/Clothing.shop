package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findFirstByName(String name);
    List<RoleEntity> findAllByNameIn(List<String> names);
}
