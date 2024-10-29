package com.menglang.Clothing.shop.services.imports;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportMapper;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.BranchRepository;
import com.menglang.Clothing.shop.repositories.ImportRepository;
import com.menglang.Clothing.shop.services.imports.importDetails.ImportDetailsService;
import com.menglang.Clothing.shop.services.stock.StockService;
import com.menglang.Clothing.shop.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    @Autowired
    private final ImportRepository importRepository;

    @Autowired
    private final ImportDetailsService importDetailsService;

    @Autowired
    private final BranchRepository branchRepository;

    @Autowired
    private final ImportMapper importMapper;

    @Autowired
    private final StockService stockService;


    @Override
    @Transactional()
    public ResponseTemplate makeImport(ImportRequest request) throws RuntimeException {
        try {
            BranchEntity branch = branchRepository.findById(request.branch()).orElseThrow(() -> new CustomMessageException("Branch Not found", "400"));
            ImportEntity importEntity = ImportEntity.builder()
                    .importNo(request.importNo())
                    .branch(branch)
                    .build();

            Set<ImportDetailsEntity> detailsSet = new HashSet<>();
            for (ImportDetailsRequest details : request.importDetails()) {
                ImportDetailsEntity detailsEntity = importDetailsService.create(details);//Validate product,color,size
                detailsEntity.setImportRecord(importEntity);
                detailsSet.add(detailsEntity);
            }

            importEntity.setImportDetails(detailsSet);
            ImportEntity importSaved = importRepository.save(importEntity);

            return ResponseTemplate.builder()
                    .message("import successful")
                    .code("201")
                    .object(importMapper.toImportDTO(importSaved))
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    //verity to update stock
    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseTemplate verifyImport(Long id) throws Exception {

        try {
            ImportEntity importEntity = getImportById(id);
            List<ImportDetailsEntity> importDetails = importEntity.getImportDetails().stream().toList();
            BranchEntity branch = importEntity.getBranch();

            for (ImportDetailsEntity detail : importDetails) {
                StockEntity stock = StockEntity.builder()
                        .size(detail.getSize())
                        .quantity(detail.getQuantity())
                        .branch(branch)
                        .color(detail.getColor())
                        .product(detail.getProduct())
                        .build();
                stockService.updateStock(id, stock, detail.getImportCost());
            }
            return ResponseTemplate.builder()
                    .code("200")
                    .message("Products has been updated Stock successful")
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseTemplate getImport(Long id) throws Exception {
        ImportEntity importEntity = getImportById(id);
        return ResponseTemplate.builder()
                .object(importMapper.toImportDTO(importEntity))
                .code("200")
                .message("successful")
                .build();
    }

    @Override
    public ResponseTemplate deleteImport(Long id) throws Exception {
       try{
           ImportEntity importData=this.getImportById(id);
           this.importRepository.delete(importData);
           return ResponseTemplate.builder()
                   .message("import was Drop successful")
                   .code("200")
                   .object("{}")
                   .build();
       }catch (Exception e){
           throw new BadRequestException(e.getMessage());
       }
    }

    @Override
    public Page<ImportEntity> getAll(int page, int limit, String sortBy, String query) {
            Pageable pageable= PageableResponse.mapPageable(page,limit,sortBy);
            return importRepository.findAllByImportNoContainingIgnoreCase(query,pageable);

    }

    private ImportEntity getImportById(Long id) throws Exception {
        return importRepository.findById(id).orElseThrow(() -> new NotFoundException("Import Id not founded"));
    }

    private void updateProductStock(ImportDetailsEntity detail) throws Exception {

    }
}
