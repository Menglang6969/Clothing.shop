package com.menglang.Clothing.shop.dto.files;

import com.menglang.Clothing.shop.constant.AppProperties;
import com.menglang.Clothing.shop.dto.files.fileSize.FileSizeResponse;
import com.menglang.Clothing.shop.entity.FileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface FileMapper {

    FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);
    Logger log = LoggerFactory.getLogger(FileMapper.class);

    @Mapping(target = "size", source = "size", qualifiedByName = "mapSize")
    @Mapping(target = "url", source = "name", qualifiedByName = "mapUrl")
    @Mapping(target = "isTrash", source = "deletedAt", qualifiedByName = "mapTrash")
    FileResponse toFilerResponse(FileEntity file);


    List<FileResponse> toFilerResponseList(List<FileEntity> files);

    @Named("mapSize")
    default FileSizeResponse mapSize(Long size) {
        log.info("map size: {}", size);
        FileSizeResponse fileSizeResponse = new FileSizeResponse();
        Long sizeKb = size / 1024;
        String fileFormatType = "KB";
        fileSizeResponse.setFormatType(fileFormatType);
        fileSizeResponse.setFormatValue(sizeKb);
        fileSizeResponse.setNormalized(sizeKb + " " + fileFormatType);
        fileSizeResponse.setOriginalValue(size);
        return fileSizeResponse;
    }

    @Named("mapUrl")
    default String mapUrl(String name) {
        AppProperties apiUrl = new AppProperties();
        log.info("map url: {}", apiUrl.getRestUri());
        return apiUrl.getApiUrl() + "/file/load/" + name;
    }

    @Named("mapTrash")
    default boolean mapTrash(Date deletedAt) {
        return deletedAt != null;
    }


//    FileResponse toFileDTO(FileEntity file);
}
