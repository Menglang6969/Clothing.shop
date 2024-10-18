package com.menglang.Clothing.shop.services.imports;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportMapper;
import com.menglang.Clothing.shop.dto.imports.ImportRequest;
import com.menglang.Clothing.shop.dto.imports.details.ImportDetailsRequest;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.ImportDetailsEntity;
import com.menglang.Clothing.shop.entity.ImportEntity;
import com.menglang.Clothing.shop.entity.StockEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.BranchRepository;
import com.menglang.Clothing.shop.repositories.ImportRepository;
import com.menglang.Clothing.shop.services.branch.BranchService;
import com.menglang.Clothing.shop.services.imports.importDetails.ImportDetailsService;
import com.menglang.Clothing.shop.services.stock.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private final BranchService branchService;
    @Autowired
    private final BranchRepository branchRepository;

    @Autowired
    private final ImportMapper importMapper;

    @Autowired
    private final StockServiceImpl stockService;


    @Override
    @Transactional()
    public ResponseErrorTemplate makeImport(ImportRequest request) throws RuntimeException {
        try {
            BranchEntity branch=branchRepository.findById(request.branch()).orElseThrow(()->new CustomMessageException("Branch Not found","400"));
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

            return ResponseErrorTemplate.builder()
                    .message("import successful")
                    .code("201")
                    .object(importMapper.toImportDTO(importSaved))
                    .build();

        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }

    //verity to update stock
    @Override
    @Transactional
    public ResponseErrorTemplate verifyImport(Long id) throws Exception {

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
                stockService.updateStock(id,stock);
            }
            return ResponseErrorTemplate.builder()
                    .code("200")
                    .message("Products has been updated Stock successful")
                    .build();
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }

    @Override
    public ResponseErrorTemplate getImport(Long id) throws Exception {
        ImportEntity importEntity = getImportById(id);
        return ResponseErrorTemplate.builder()
                .object(importMapper.toImportDTO(importEntity))
                .code("200")
                .message("successful")
                .build();
    }

    @Override
    public ResponseErrorTemplate deleteImport(Long id) throws Exception {
        return null;
    }

    private ImportEntity getImportById(Long id) throws Exception {
        return importRepository.findById(id).orElseThrow(() -> new CustomMessageException("Import Id not founded", "400"));
    }

    private void updateProductStock(ImportDetailsEntity detail) throws Exception {

    }
}
