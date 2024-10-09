package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity,Long> {
}
