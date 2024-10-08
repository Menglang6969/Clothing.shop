package com.menglang.Clothing.shop.services.category;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryInterface {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseErrorTemplate create(CategoryRequest categoryRequest) {
        try {
            log.info("request data: {}", categoryRequest);
            Optional<CategoryEntity> parent= Optional.empty();
            if(categoryRequest.parent()!=null){
                parent = categoryRepository.findById(categoryRequest.parent());
            }
            CategoryEntity isParent = (parent.isPresent()) ? isParent = parent.get() : null;
            log.info("parent data: {}", parent);
            CategoryEntity category = CategoryEntity.builder()
                    .name(categoryRequest.name())
                    .parent(isParent)
                    .level(categoryRequest.level())
                    .description(categoryRequest.description())
                    .build();
            log.info("category entity: {}", category);

            categoryRepository.save(category);

            return ResponseErrorTemplate.builder()
                    .message("Category is created")
                    .code("201")
                    .object(category)
                    .build();

        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "500");
        }
    }

    @Override
    public ResponseErrorTemplate update(Long id, CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public ResponseErrorTemplate delete(Long id) {
        return null;
    }

    @Override
    public ResponseErrorTemplate getAll() {
        return null;
    }
}
