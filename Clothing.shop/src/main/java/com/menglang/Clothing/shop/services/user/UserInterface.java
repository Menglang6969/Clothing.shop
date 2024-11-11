package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.dto.auth.AuthenticationRequest;
import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.user.UserRequest;

public interface UserInterface {

    ResponseTemplate create(UserRequest userRequest);
    ResponseTemplate findById(Long id);
    ResponseTemplate findByUsername(String username);
    ResponseTemplate authenticate(AuthenticationRequest data);
}
