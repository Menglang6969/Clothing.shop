package com.menglang.Clothing.shop.dto.imports;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;
import com.menglang.Clothing.shop.entity.ImportEntity;
import jakarta.persistence.Table;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImportMapper {
    ImportMapper INSTANCE= Mappers.getMapper(ImportMapper.class);

    @Mapping(target = "branch",source = "branch",qualifiedByName = "mapBranch")
    @Mapping(target = "importDetails", source = "importDetails",qualifiedByName = "mapImportDetails")
    ImportResponse toImportDTO(ImportEntity data);

    BranchDTO toBranchDTO(BranchEntity branch);

    @Mapping(target ="color", source = "color.name")
    @Mapping(target ="size", source = "size.name")
    ImportDetailsResponse toImportDetailsDTO(ImportDetailsEntity details);

    @Named("mapBranch")
    default BranchDTO mapBranch(BranchEntity branch){
        return toBranchDTO(branch);
    }

    @Named("mapImportDetails")
    default ImportDetailsResponse mapImportDetails(ImportDetailsEntity details){
        return toImportDetailsDTO(details);
    }
}
