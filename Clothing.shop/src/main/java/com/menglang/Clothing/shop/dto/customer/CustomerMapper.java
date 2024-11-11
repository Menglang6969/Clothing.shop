package com.menglang.Clothing.shop.dto.customer;

import com.menglang.Clothing.shop.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponse toCustomerDTO(CustomerEntity customer);
}
