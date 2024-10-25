package com.menglang.Clothing.shop.dto.order.orderDetails;

import com.menglang.Clothing.shop.dto.product.ProductDTO;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemResponse;
import com.menglang.Clothing.shop.entity.OrderItemsEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {
    OrderDetailsMapper INSTANCE= Mappers.getMapper(OrderDetailsMapper.class);


    @Mapping(target = "size", source = "size.name")
    @Mapping(target = "color", source = "color.name")
    @Mapping(target = "product",source = "product",qualifiedByName = "mapProduct")
    OrderDetailsResponse toOrderItemDTO(OrderItemsEntity items);

    ProductDTO toProductDTO(ProductEntity product);

    @Named("mapProduct")
    default ProductDTO mapProduct(ProductEntity product){
        return this.toProductDTO(product);
    }
}
