package com.menglang.Clothing.shop.dto.export;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.export.details.ExportDetailsResponse;
import com.menglang.Clothing.shop.dto.imports.ImportResponse;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsResponse;
import com.menglang.Clothing.shop.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExportMapper {
    ExportMapper INSTANCE= Mappers.getMapper(ExportMapper.class);

    @Mapping(target = "fromBranch",source = "fromBranch",qualifiedByName = "mapBranch")
    @Mapping(target = "toBranch",source = "toBranch",qualifiedByName = "mapBranch")
    @Mapping(target = "exportDetails", source = "exportDetails",qualifiedByName = "mapExportDetails")
    ExportResponse toExportDTO(ExportEntity data);

    @Mapping(target ="color", source = "color.name")
    @Mapping(target ="size", source = "size.name")
    ExportDetailsResponse toExportDetailsDTO(ExportDetailsEntity details);

    BranchDTO toBranchDTO(BranchEntity branch);


    @Named("mapBranch")
    default BranchDTO mapBranch(BranchEntity branch){
        return toBranchDTO(branch);
    }

    @Named("mapExportDetails")
    default ExportDetailsResponse mapExportDetails(ExportDetailsEntity details){
        return toExportDetailsDTO(details);
    }
}
