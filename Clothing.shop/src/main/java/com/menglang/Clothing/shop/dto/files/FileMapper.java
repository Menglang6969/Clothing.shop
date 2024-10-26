package com.menglang.Clothing.shop.dto.files;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

//    FileResponse toFileDTO(FileEntity file);
}
