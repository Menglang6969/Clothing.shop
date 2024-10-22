package com.menglang.Clothing.shop;

import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.RoleEntity;
import com.menglang.Clothing.shop.repositories.ColorRepository;
import com.menglang.Clothing.shop.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
