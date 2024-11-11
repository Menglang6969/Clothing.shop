package com.menglang.Clothing.shop.services.category;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryRequest;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.enums.SortBy;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.ConflictException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.CategoryRepository;
import com.menglang.Clothing.shop.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryInterface {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseTemplate create(CategoryRequest categoryRequest) {
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

            return ResponseTemplate.builder()
                    .message("Category is created")
                    .code("201")
                    .object(category)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseTemplate update(Long id, CategoryRequest categoryRequest) {

        if (categoryRepository.existsByNameAndIdNot(categoryRequest.name(), id)) {
            throw new ConflictException("Category name already exists: ");
        }

        Optional<CategoryEntity> categoryOptional = Optional.ofNullable(categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not founded.")));

        try {
            if (categoryOptional.isPresent()) {
                CategoryEntity existCategory = categoryOptional.get();
                CategoryEntity isParent = validateCategory(categoryRequest);
                existCategory.setDescription(categoryRequest.description());
                existCategory.setLevel(categoryRequest.level());
                existCategory.setName(categoryRequest.name());
                existCategory.setParentId(isParent);

                log.info("update category entity: {} {}", existCategory.getParentId(), isParent);
                categoryRepository.save(existCategory);

                return ResponseTemplate.builder()
                        .message("Category is created")
                        .code("201")
                        .object(existCategory)
                        .build();
            }
            return ResponseTemplate.builder()
                    .message("Category is Missing")
                    .code("203")
                    .object(null)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseTemplate delete(Long id) {

        try{
            boolean category=categoryRepository.existsById(id);
            if(category){
                categoryRepository.deleteById(id);
                return ResponseTemplate.builder()
                        .message("Category is deleted successful")
                        .code("200")
                        .build();
            }else{
                throw new NotFoundException("Category Not found");
            }
        }catch (Exception e){
           throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public Page<CategoryEntity> getAll(int page, int limit, String sort, String query) {//createdAt:desc
        Pageable pageable= PageableResponse.mapPageable(page-1,limit,sort);
        return categoryRepository.findByNameContainingIgnoreCase(query,pageable);
    }


    @Override
    public ResponseTemplate findOne(Long id) {
        CategoryEntity category=findCategoryById(id);
        return ResponseTemplate.builder()
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
               throw new ConflictException(name+" is Exist");
           }
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
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
