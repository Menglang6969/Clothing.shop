package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.UserRequest;
import com.menglang.Clothing.shop.entity.UserEntity;

public interface UserInterface {

    ResponseErrorTemplate create(UserRequest userRequest);
    ResponseErrorTemplate findById(Long id);
    ResponseErrorTemplate findByUsername(String username);
}
