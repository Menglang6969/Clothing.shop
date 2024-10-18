package com.menglang.Clothing.shop.dto.stock;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockMapper INSTANCE= Mappers.getMapper(StockMapper.class);


}
