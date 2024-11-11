package com.menglang.Clothing.shop.dto.branch;

import com.menglang.Clothing.shop.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    BranchResponse toBranchDTO(BranchEntity branch);

    @Mapping(target = "description", source = "description",ignore = true)
    @Mapping(target = "address",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "createdBy",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "updatedBy",ignore = true)
    BranchResponse toDTO(BranchEntity branch);
}
