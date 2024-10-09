package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import com.menglang.Clothing.shop.repositories.ColorRepository;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductInterface {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ColorRepository colorRepository;

    @Autowired
    private final UserServiceImp userService;

    @Autowired
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseErrorTemplate create(ProductRequest product) {

        log.info("sizes: {}",product.sizes());
        try {
            Set<ColorEntity> colorsSet = new HashSet<>();
            for(Long color: product.colors()){
                Optional<ColorEntity> existColor=colorRepository.findById(color);
                existColor.ifPresent(colorsSet::add);
            }
            log.info("colorsSet: {}",colorsSet);

            CategoryEntity category = categoryRepository.findById(product.category()).orElseThrow(() -> new CustomMessageException("Category does not exist", "400"));
            ProductEntity product_data = ProductEntity.builder()
                    .title(product.title())
                    .colors(colorsSet)
                    .description(product.description())
                    .discountedPrice(product.discountPrice())
                    .discountedPercent(product.discountPercentage())
                    .imageUrl(product.imageUrl())
                    .brand(product.brand())
                    .price(product.price())
                    .sizes(product.sizes())
                    .quantity(product.quantity())
                    .category(category)
                    .build();

            productRepository.save(product_data);
            return ResponseErrorTemplate.builder()
                    .message("Product Created Successful")
                    .code("201")
                    .object(product_data)
                    .build();
        } catch (Exception e) {
            log.info("product error: {}",e.getLocalizedMessage());
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }
//
//    private CategoryEntity findOrCreateCategory(String topCategory, String secondCategory, String thirdCategory) {
//
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
//
//    }

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
    public List<ProductEntity> searchProduct(String query) throws Exception {
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
