package com.menglang.Clothing.shop.services.product;

import com.menglang.Clothing.shop.dto.product.ProductRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.embedded.Size;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.services.user.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductInterface{

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserServiceImp userService;

    @Autowired
    private final CategoryRepository categoryRepository;


    @Override
    public void create(ProductRequest product) {
        CategoryEntity topLevel=categoryRepository.findByName(product.getTopLevelCategory());
        CategoryEntity secondLevel=categoryRepository.findByNameAndParent(product.getSecondLevelCategory(),
                product.getTopLevelCategory());
        CategoryEntity ThirdLevel=categoryRepository.findByName(product.getThirdLevelCategory());



    }

    private void findOrCreateCategory(String name,Integer level ){
        CategoryEntity category=categoryRepository.findByName(name);
        if (category==null){
            CategoryEntity categoryObj=CategoryEntity.builder()
                    .name(name)
                    .level(level)
                    .build();
            category=categoryRepository.save(categoryObj);
        }
    }

    @Override
    public List<ProductEntity> getProducts() {
        return List.of();
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return null;
    }

    @Override
    public ProductEntity updateProduct(Long id, ProductEntity product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

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
    }
}
