package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.AuthenticationRequest;
import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.UserRequest;
import com.menglang.Clothing.shop.dto.UserResponse;
import com.menglang.Clothing.shop.repositories.UserRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class AuthControllers {

    private static final Logger log = LoggerFactory.getLogger(AuthControllers.class);
    @Autowired
    private final UserServiceImp user;

    @PostMapping("/accounts/register")
    public ResponseEntity<ResponseErrorTemplate> register(@RequestBody UserRequest userRequest) throws Exception {
        log.info("Intercept register new user with req: {}",userRequest);
        return ResponseEntity.ok(user.create(userRequest));
    }

    @PostMapping("/accounts/login")
    public ResponseEntity<ResponseErrorTemplate> authenticate(@RequestBody AuthenticationRequest data) throws Exception {
        log.info("Intercept register new user with req: {}",data);
        return ResponseEntity.ok(user.authenticate(data));
    }







}
