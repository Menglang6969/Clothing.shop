package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.entity.UserEntity;

public interface UserInterface {
    public UserEntity findUserByUsername(String username) throws Exception;
    public UserEntity findUserById(Long id) throws Exception;
    public UserEntity findUserByJwt(String jwt) throws Exception;
}
