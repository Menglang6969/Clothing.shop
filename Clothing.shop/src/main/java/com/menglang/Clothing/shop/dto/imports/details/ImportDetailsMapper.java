package com.menglang.Clothing.shop.dto.imports.details;

import com.menglang.Clothing.shop.dto.category.CategoryDTO;
import com.menglang.Clothing.shop.dto.product.ProductDTO;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImportDetailsMapper {
    ImportDetailsMapper INSTANCE = Mappers.getMapper(ImportDetailsMapper.class);

    @Mapping(target = "size", source = "size.name")
    @Mapping(target = "color", source = "color.name")
    @Mapping(target = "product", source = "product", qualifiedByName = "mapProduct")
    ImportDetailsResponse toImportDetailsDTO(ImportDetailsEntity details);

    ProductDTO toProductDTO(ProductEntity product);

    @Named("mapProduct")
    default ProductDTO mapProduct(ProductEntity product) {
        return toProductDTO(product);
    }
}
