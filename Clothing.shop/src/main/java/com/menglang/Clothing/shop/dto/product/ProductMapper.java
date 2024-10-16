package com.menglang.Clothing.shop.dto.product;

import com.menglang.Clothing.shop.dto.category.CategoryDTO;
import com.menglang.Clothing.shop.dto.colors.ColorDTO;
import com.menglang.Clothing.shop.dto.size.SizeDTO;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "category", source = "category",qualifiedByName = "mapCategory")
    @Mapping(target = "colors", source = "colors",qualifiedByName = "mapColors")
    @Mapping(target = "sizes", source = "sizes", qualifiedByName = "mapSizes")
    ProductResponse toProductDTO(ProductEntity product);

    ColorDTO toColorDTO(ColorEntity color);
    SizeDTO toSizeDTO(SizeEntity size);
    CategoryDTO toCategoryDTO(CategoryEntity category);

    @Named("mapCategory")
    default CategoryDTO mapCategory(CategoryEntity category) {
        return toCategoryDTO(category);
    }

    @Named("mapColors")
    default List<ColorDTO> mapColors(Set<ColorEntity> colors) {
        return colors.stream().map(this::toColorDTO).collect(Collectors.toList());
    }

    @Named("mapSizes")
    default List<SizeDTO> mapSizes(Set<SizeEntity> sizes) {
        return sizes.stream().map(this::toSizeDTO).collect(Collectors.toList());
    }



}
