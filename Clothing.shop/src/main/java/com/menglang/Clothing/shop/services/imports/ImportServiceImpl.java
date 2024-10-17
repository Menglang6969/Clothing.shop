package com.menglang.Clothing.shop.services.imports;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportMapper;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;
import com.menglang.Clothing.shop.entity.ImportEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.ImportRepository;
import com.menglang.Clothing.shop.services.branch.BranchService;
import com.menglang.Clothing.shop.services.branch.BranchServiceImpl;
import com.menglang.Clothing.shop.services.imports.importDetails.ImportDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService{

    @Autowired
    private final ImportRepository importRepository;

    @Autowired
    private final ImportDetailsService importDetailsService;

    @Autowired
    private final BranchServiceImpl branchService;

    @Autowired
    private final ImportMapper importMapper;

    @Override
    public ResponseErrorTemplate makeImport(ImportRequest request) throws Exception {

       try{
           Set<ImportDetailsEntity> detailsSet=new HashSet<>();
           for(ImportDetailsRequest details: request.importDetails()){
               ImportDetailsEntity detailsEntity=importDetailsService.create(details);
               detailsSet.add(detailsEntity);
           }

           BranchEntity branch= (BranchEntity) branchService.findBranchById(request.branch()).object();

           ImportEntity importEntity=ImportEntity.builder()
                   .importNo(request.importNo())
                   .importDetails(detailsSet)
                   .branch(branch)
                   .build();
          ImportEntity importSaved= importRepository.save(importEntity);

           return ResponseErrorTemplate.builder()
                   .message("import successful")
                   .code("201")
                   .object(importMapper.toImportDTO(importSaved))
                   .build();

       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"400");
       }
    }

    //verity to update stock
    @Override
    public void verifyImport(Long id) throws Exception {

    }

    @Override
    public ResponseErrorTemplate getImport(Long id) throws Exception {
        ImportEntity importEntity=importRepository.findById(id).orElseThrow(()->new CustomMessageException("Import Id not founded","400"));
        return ResponseErrorTemplate.builder()
                .object(importMapper.toImportDTO(importEntity))
                .code("200")
                .message("successful")
                .build();
    }
}
