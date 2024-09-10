package com.menglang.Clothing.shop;

import com.menglang.Clothing.shop.entity.RoleEntity;
import com.menglang.Clothing.shop.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			RoleEntity role =new RoleEntity();
			role.setId(0L);
			role.setName("USER");
			roleRepository.save(role);
			role.setId(0L);
			role.setName("ADMIN");
			roleRepository.save(role);
		};
	}

}
