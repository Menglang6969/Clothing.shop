package com.menglang.Clothing.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/")
@RestController
public class IndexController {
    @GetMapping(value = {"api/doc","api/docs"})
    public RedirectView apiDoc(){
        return new RedirectView("/swagger-ui/index.html");
    }
}

