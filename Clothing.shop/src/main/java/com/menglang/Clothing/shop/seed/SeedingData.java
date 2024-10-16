package com.menglang.Clothing.shop.seed;

import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class SeedingData {

    private static final Logger log = LoggerFactory.getLogger(SeedingData.class);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ColorRepository colorRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;

    @PostConstruct
    private void seeding() {
        log.info(" seeding data..........................................");
        seedColors();
        seedRoles();
        seedCategory();
        seedSize();
        seedProduct();
    }

    private void seedProduct() {
        CategoryEntity category = categoryRepository.findById(2L).orElseThrow(() -> new CustomMessageException("Category does not exist", "400"));
        Set<ColorEntity> colorsSet = new HashSet<>();
        for (Long color : List.of(1L, 2L)) {
            Optional<ColorEntity> existColor = colorRepository.findById(color);
            existColor.ifPresent(colorsSet::add);
        }

        Set<SizeEntity> productSizes = new HashSet<>();
        for (Long size : List.of(1L, 2L)) {
            Optional<SizeEntity> existSize = sizeRepository.findById(size);
            existSize.ifPresent(productSizes::add);
        }

        ProductEntity p1 = ProductEntity.builder()
                .title("NIKE")
                .category(category)
                .description("Made from Cambodia")
                .baseCost(BigDecimal.valueOf(5.55))
                .colors(colorsSet)
                .sizes(productSizes)
                .sellCost(BigDecimal.valueOf(8.99))
                .imageUrl("https://localhost:image_url")
                .build();

        ProductEntity p2 = ProductEntity.builder()
                .title("Adidas")
                .category(category)
                .description("Made from Cambodia")
                .baseCost(BigDecimal.valueOf(5.55))
                .colors(colorsSet)
                .sizes(productSizes)
                .sellCost(BigDecimal.valueOf(8.99))
                .imageUrl("https://localhost:image_url")
                .build();

        ProductEntity p3 = ProductEntity.builder()
                .title("Jodan")
                .category(category)
                .description("Made from Cambodia")
                .baseCost(BigDecimal.valueOf(5.55))
                .colors(colorsSet)
                .sizes(productSizes)
                .sellCost(BigDecimal.valueOf(8.99))
                .imageUrl("https://localhost:image_url")
                .build();

        List<ProductEntity> productEntities = List.of(p1, p2, p3);
        productRepository.saveAll(productEntities);

    }

    private void seedSize() {
        SizeEntity size1 = SizeEntity.builder().name("S").build();
        SizeEntity size2 = SizeEntity.builder().name("M").build();
        SizeEntity size3 = SizeEntity.builder().name("L").build();
        SizeEntity size4 = SizeEntity.builder().name("XL").build();
        SizeEntity size5 = SizeEntity.builder().name("2XL").build();
        List<SizeEntity> sizes = List.of(size1, size2, size3, size4, size5);
        sizeRepository.saveAll(sizes);
    }

    private void seedRoles() {
        RoleEntity role = new RoleEntity();
        role.setId(0L);
        role.setName("USER");
        roleRepository.save(role);
        role.setId(0L);
        role.setName("ADMIN");
        roleRepository.save(role);
    }

    private void seedColors() {
        List<String> colors = List.of("WHITE", "BLACK", "GRAY", "BLUE", "RED", "PINK");
        ColorEntity colorEntity = new ColorEntity();
        for (String color : colors) {
            colorEntity.setName(color);
            colorEntity.setId(0L);
            colorRepository.save(colorEntity);
        }
    }

    private void seedCategory() {
        List<CategoryEntity> categories = new ArrayList<>();
        CategoryEntity category1 = CategoryEntity.builder()
                .name("T-Shirt")
                .level(1)
                .description("T-Shirt for Men")
                .build();
        categories.add(category1);
        CategoryEntity category2 = CategoryEntity.builder()
                .name("Shoes")
                .level(1)
                .description("Shoes for Men")
                .build();
        categories.add(category2);
        CategoryEntity category3 = CategoryEntity.builder()
                .name("Sneaker")
                .level(2)
                .parentId(category2)
                .description("Sneaker for Men")
                .build();
        categories.add(category3);
        categoryRepository.saveAll(categories);
    }

    private void seedUsers() {

    }
}
