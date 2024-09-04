package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthControllers {

    @Autowired
    private final UserRepository user;



}
