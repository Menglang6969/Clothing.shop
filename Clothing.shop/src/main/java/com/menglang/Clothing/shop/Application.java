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

//	@Bean
//	CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
//		return args -> {
//			RoleEntity role =new RoleEntity();
//			role.setId(0L);
//			role.setName("USER");
//			roleRepository.save(role);
//			role.setId(0L);
//			role.setName("ADMIN");
//			roleRepository.save(role);
//		};
//	}
//
//    @Bean
//    CommandLineRunner commandLineRunner(ColorRepository colorRepository) {
//        return args -> {
//            List<String> colors = List.of("WHITE", "BLACK", "GRAY", "BLUE", "RED", "PINK");
//            ColorEntity colorEntity = new ColorEntity();
//            for (String color : colors) {
//                colorEntity.setName(color);
//                colorEntity.setId(0L);
//                colorRepository.save(colorEntity);
//            }
//
//        };
//    }

}
