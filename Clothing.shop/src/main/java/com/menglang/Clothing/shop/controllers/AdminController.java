package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserServiceImp userService;

    @GetMapping("/accounts")
    public ResponseEntity<Object> index(Principal principal) throws Exception {
        var username=principal.getName();
        log.info("admin : username {} request get user data",username);
        return ResponseEntity.ok(userService.findByUsername(username));
    }
}
