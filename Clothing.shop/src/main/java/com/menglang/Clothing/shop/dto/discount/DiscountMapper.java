package com.menglang.Clothing.shop.dto.discount;

import com.menglang.Clothing.shop.entity.OrderItemsEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    DiscountMapper INSTANCE= Mappers.getMapper(DiscountMapper.class);

    Set<ItemCalculateType> OrderToCalculateType(Set<OrderItemsEntity> items);

    Set<ItemCalculateType> PurchaseToCalculateType(Set<PurchaseItemEntity> items);
}
