package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserInterface{

    @Override
    public UserEntity findUserByUsername(String username) throws Exception {
        return null;
    }

    @Override
    public UserEntity findUserById(Long id) throws Exception {
        return null;
    }

    @Override
    public UserEntity findUserByJwt(String jwt) throws Exception {
        return null;
    }
}
