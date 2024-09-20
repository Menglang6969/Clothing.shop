package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductInterface {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserServiceImp userService;

    @Autowired
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseErrorTemplate create(ProductRequest product) {
        CategoryEntity category = findOrCreateCategory(
                product.getTopLevelCategory(),
                product.getSecondLevelCategory(),
                product.getThirdLevelCategory());

        ProductEntity product_data = ProductEntity.builder()
                .title(product.getTitle())
                .color(product.getColor())
                .description(product.getDescription())
                .discountedPrice(product.getDiscountPrice())
                .discountedPercent(product.getDiscountPercentage())
                .imageUrl(product.getImageUrl())
                .brand(product.getBrand())
                .price(product.getPrice())
                .sizes(product.getSizes())
                .quantity(product.getQuantity())
                .category(category)
                .build();
        try {
            productRepository.save(product_data);
            return ResponseErrorTemplate.builder()
                    .message("Product Created Successful")
                    .code("201")
                    .build();
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }

    private CategoryEntity findOrCreateCategory(String topCategory, String secondCategory, String thirdCategory) {

//        CategoryEntity topLevel = categoryRepository.findByName(topCategory);
//        if (topLevel == null) {
//            CategoryEntity categoryObj = CategoryEntity.builder()
//                    .name(topCategory)
//                    .level(1)
//                    .build();
//            topLevel = categoryRepository.save(categoryObj);
//        }
//
//        CategoryEntity secondLevel = categoryRepository.findByNameAndParent(secondCategory, topCategory);
//        if (secondLevel == null) {
//            CategoryEntity categoryObj = CategoryEntity.builder()
//                    .name(secondCategory)
//                    .level(2)
//                    .parent(topLevel)
//                    .build();
//            secondLevel = categoryRepository.save(categoryObj);
//        }
//
//        CategoryEntity thirdLevel = categoryRepository.findByNameAndParent(thirdCategory, secondCategory);
//        if (thirdLevel == null) {
//            CategoryEntity categoryObj = CategoryEntity.builder()
//                    .name(thirdCategory)
//                    .level(3)
//                    .parent(secondLevel)
//                    .build();
//            thirdLevel = categoryRepository.save(categoryObj);
//        }
//        return thirdLevel;
        return null;
    }

    @Override
    public List<ProductEntity> getProducts() {
        return List.of();
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() ->
                new CustomMessageException("Product Not Found", "404"));
    }

    @Override
    public ResponseErrorTemplate updateProduct(Long id, ProductEntity data) {
        ProductEntity product = getProductById(id);
        if (data.getQuantity() != 0) {
            product.setQuantity(data.getQuantity());

        }
        try {
            this.productRepository.save(product);
            return ResponseErrorTemplate.builder()
                    .message("Product Updated Successful")
                    .code("200")
                    .build();
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }


    }

    @Override
    public ResponseErrorTemplate deleteProduct(Long id) {
        ProductEntity product = getProductById(id);
        product.getSizes().clear();
        try {
            this.productRepository.delete(product);
            return ResponseErrorTemplate.builder()
                    .message("Product Deleted Successful")
                    .code("200")
                    .build();
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }

    @Override
    public List<ProductEntity> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<ProductEntity> getAllProducts(String category,
                                              List<String> colors,
                                              List<Size> sizes,
                                              Integer minPrice,
                                              Integer maxPrice,
                                              Integer discount,
                                              String sort,
                                              String stock,
                                              Integer pageNumber,
                                              Integer pageSize) {

        return null;
//        Pageable pageable = PageRequest.of(
//                pageNumber,
//                pageSize
//        );
//        List<ProductEntity> products = productRepository.filterProducts(
//                category,
//                minPrice,
//                maxPrice,
//                discount,
//                sort
//        );
//
//        if (!colors.isEmpty()) {
//            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
//                    .toList();
//        }
//
//        if (stock != null) {
//            if (stock.equals("in_stock")) {
//                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
//            } else if (stock.equals("out_of_stock")) {
//                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
//            }
//        }
//
//        int startIndex = (int) pageable.getOffset();
//        int endIndex = (int) Math.min(startIndex + pageable.getPageSize(), products.size());
//
//        List<ProductEntity> pageContent = products.subList(startIndex, endIndex);
//        return new PageImpl<>(pageContent, pageable, products.size());

    }
}
