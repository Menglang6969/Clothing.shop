package com.menglang.Clothing.shop.services.category;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
            if(categoryRepository.existsByName(categoryRequest.name())){
                throw new CustomMessageException( "Category name already exists: ", String.valueOf(HttpStatus.CONFLICT.value()));
            }
            CategoryEntity isParent = validateCategory(categoryRequest);
            CategoryEntity category = CategoryEntity.builder()
                    .name(categoryRequest.name())
                    .parentId(isParent)
                    .level(categoryRequest.level())
                    .description(categoryRequest.description())
                    .build();
            log.info("category entity: {} {}", category.getParentId(), isParent);

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

        if (categoryRepository.existsByNameAndIdNot(categoryRequest.name(), id)) {
            throw new CustomMessageException( "Category name already exists: ", String.valueOf(HttpStatus.CONFLICT.value()));
        }

        Optional<CategoryEntity> categoryOptional= Optional.ofNullable(categoryRepository.findById(id).orElseThrow(() -> new CustomMessageException("Category not founded.", "400")));

        try{
            if (categoryOptional.isPresent()){
                CategoryEntity existCategory=categoryOptional.get();
                CategoryEntity isParent=validateCategory(categoryRequest);
                existCategory.setDescription(categoryRequest.description());
                existCategory.setLevel(categoryRequest.level());
                existCategory.setName(categoryRequest.name());
                existCategory.setParentId(isParent);

                log.info("update category entity: {} {}", existCategory.getParentId(), isParent);
                categoryRepository.save(existCategory);

                return ResponseErrorTemplate.builder()
                        .message("Category is created")
                        .code("201")
                        .object(existCategory)
                        .build();
            }

        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"500");
        }
        return null;
    }

    @Override
    public ResponseErrorTemplate delete(Long id) {

        try{
            boolean category=categoryRepository.existsById(id);
            if(category){
                categoryRepository.deleteById(id);
                return ResponseErrorTemplate.builder()
                        .message("Category is deleted successful")
                        .code("200")
                        .build();
            }else{
                throw new CustomMessageException("Category to be delete does not exist","400");
            }
        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(), "500");
        }

    }

    @Override
    public ResponseErrorTemplate getAll() {
        return null;
    }

    @Override
    public ResponseErrorTemplate findOne(Long id) {
        CategoryEntity category=findCategoryById(id);
        return ResponseErrorTemplate.builder()
                .object(category)
                .code("200")
                .message("category founded")
                .build();
    }

    private CategoryEntity validateCategory(CategoryRequest request) {
        log.info("request data: {}", request);

        Optional<CategoryEntity> parent = Optional.empty();
        if (request.parent() != null) {
            parent = categoryRepository.findById(request.parent());
        }
        return parent.orElse(null);

    }

    private void isExistCategory(String name){
        try{
           Optional<CategoryEntity> category= categoryRepository.findByName(name);
           if(category.isPresent()){
               throw new CustomMessageException(name+" is Exist","400");
           }
        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"500");
        }
    }

    private CategoryEntity findCategoryById(Long id){
        Optional<CategoryEntity> category= Optional.ofNullable(categoryRepository.findById(id).orElseThrow(() -> CustomMessageException.builder()
                .code("400")
                .message("Category of found")
                .build()));
        return category.orElse(null);
    }
}
