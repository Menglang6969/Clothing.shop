package com.menglang.Clothing.shop.dto.purchase.purchaseItems;

import com.menglang.Clothing.shop.dto.product.ProductDTO;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemOrderMapper {
    ItemOrderMapper INSTANCE= Mappers.getMapper(ItemOrderMapper.class);

    @Mapping(target = "size", source = "size.name")
    @Mapping(target = "color", source = "color.name")
    @Mapping(target = "product",source = "product",qualifiedByName = "mapProduct")
    ItemResponse toItemOrderDTO(PurchaseItemEntity itemEntity);

    ProductDTO toProductDTO(ProductEntity product);

    @Named("mapProduct")
    default ProductDTO mapProduct(ProductEntity product){
        return this.toProductDTO(product);
    }
}
