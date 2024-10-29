package com.menglang.Clothing.shop.dto.category;

import com.menglang.Clothing.shop.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE= Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "parent",source = "parentId",qualifiedByName = "mapParentCategory")
    CategoryResponse toCategoryDTO(CategoryEntity category);

    CategoryDTO parentDto(CategoryEntity category);

    @Named("mapParentCategory")
    default CategoryDTO mapParentCategory(CategoryEntity category){
        return parentDto(category);
    }
}
