package com.menglang.Clothing.shop.repositories;

import com.menglang.Clothing.shop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    UserEntity findUserByEmail(String username);

}
