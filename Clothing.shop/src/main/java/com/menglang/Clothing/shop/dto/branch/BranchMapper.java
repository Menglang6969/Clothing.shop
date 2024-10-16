package com.menglang.Clothing.shop.dto.branch;

import com.menglang.Clothing.shop.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    BranchResponse toBranchDTO(BranchEntity branch);
}
