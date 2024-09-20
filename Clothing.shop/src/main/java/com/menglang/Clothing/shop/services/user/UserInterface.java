package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.dto.auth.AuthenticationRequest;
import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.user.UserRequest;

public interface UserInterface {

    ResponseErrorTemplate create(UserRequest userRequest);
    ResponseErrorTemplate findById(Long id);
    ResponseErrorTemplate findByUsername(String username);
    ResponseErrorTemplate authenticate(AuthenticationRequest data);
}
