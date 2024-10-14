package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.product.ProductMapper;
import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.dto.product.ProductResponse;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import com.menglang.Clothing.shop.repositories.ColorRepository;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.repositories.SizeRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final SizeRepository sizeRepository;

    @Autowired
    private final UserServiceImp userService;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ResponseErrorTemplate create(ProductRequest product) {

        log.info("sizes: {}", product.sizes());
        try {

            Set<ColorEntity> colorsSet = new HashSet<>();
            for (Long color : product.colors()) {
                Optional<ColorEntity> existColor = colorRepository.findById(color);
                existColor.ifPresent(colorsSet::add);
            }

            Set<SizeEntity> productSizes = new HashSet<>();
            for (Long size : product.sizes()) {
                Optional<SizeEntity> existSize = sizeRepository.findById(size);
                existSize.ifPresent(productSizes::add);
            }


            CategoryEntity category = categoryRepository.findById(product.category()).orElseThrow(() -> new CustomMessageException("Category does not exist", "400"));
            ProductEntity product_data = ProductEntity.builder()
                    .title(product.title())
                    .sellCost(product.sellCost())
                    .baseCost(product.baseCost())
                    .description(product.description())
                    .discountedPrice(product.discountedPrice())
                    .discountedPercent(product.discountedPercent())
                    .imageUrl(product.imageUrl())
                    .sizes(productSizes)
                    .colors(colorsSet)
                    .category(category)
                    .build();

            ProductEntity resProduct = productRepository.save(product_data);
            ProductResponse productDto = productMapper.toProductDTO(resProduct);

            return ResponseErrorTemplate.builder()
                    .message("Product Created Successful")
                    .code("201")
                    .object(productDto)
                    .build();
        } catch (Exception e) {
            log.info("product error: {}", e.getLocalizedMessage());
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }

    @Override
    public List<ProductEntity> getProducts() {
        return List.of();
    }

    @Override
    public ResponseErrorTemplate getProductById(Long id) {
        ProductEntity product = findProductById(id);
        ProductResponse productResponse = productMapper.toProductDTO(product);
        return ResponseErrorTemplate.builder().message("successful")
                .code("200")
                .object(productResponse)
                .build();
    }

    public ProductEntity findProductById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() ->
                new CustomMessageException("Product Not Found", "404"));
    }


    @Override
    public ResponseErrorTemplate updateProduct(Long id, ProductRequest data) {
//        ProductEntity product = findProductById(id);
//        if (data.quantity() != 0) {
//            product.setQuantity(data.quantity());
//        }
//        try {
//            ProductEntity updatedProduct = this.productRepository.save(product);
//            ProductResponse productResponse= productMapper.toProductDTO(updatedProduct);
//            return ResponseErrorTemplate.builder()
//                    .message("Product Updated Successful")
//                    .code("200")
//                    .object(productResponse)
//                    .build();
//        } catch (Exception e) {
//            throw new CustomMessageException(e.getMessage(), "500");
//        }
        return null;


    }

    @Override
    public ResponseErrorTemplate deleteProduct(Long id) {
        ProductEntity product = findProductById(id);
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
    public Page<ProductEntity> getAllProducts(
            Long categoryId,
            Double minPrice,
            Double maxPrice,
            List<Long> colorIds,
            List<Long> sizeIds,
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDir

    ) {

        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

            Page<ProductEntity> products= productRepository.filterProducts(categoryId, minPrice, maxPrice, colorIds,pageable);

            log.info("products data {}", products);
    return null;
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }

    }
}
