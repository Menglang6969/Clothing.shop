package com.menglang.Clothing.shop.services.export;


import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.export.ExportMapper;
import com.menglang.Clothing.shop.dto.export.ExportRequest;
import com.menglang.Clothing.shop.dto.export.details.ExportDetailsRequest;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.ExportDetailsEntity;
import com.menglang.Clothing.shop.entity.ExportEntity;
import com.menglang.Clothing.shop.entity.StockEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.ExportRepository;
import com.menglang.Clothing.shop.repositories.StockRepository;
import com.menglang.Clothing.shop.services.export.exportDetails.ExportDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private static final Logger log = LoggerFactory.getLogger(ExportServiceImpl.class);
    @Autowired
    private final GetEntitiesById getEntity;
    @Autowired
    private final ExportRepository exportRepository;
    @Autowired
    private final ExportMapper exportMapper;
    @Autowired
    private final ExportDetailsService exportDetailsService;
    @Autowired
    private final StockRepository stockRepository;


    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseErrorTemplate makeExport(ExportRequest request) throws Exception {
        log.info("invoke create export..................");
        try {
            BranchEntity fromBranch = getEntity.findBranchById(request.fromBranch());
            BranchEntity toBranch = getEntity.findBranchById(request.toBranch());
            ExportEntity exportEntity = ExportEntity.builder()
                    .exportNo(request.exportNo())
                    .fromBranch(fromBranch)
                    .toBranch(toBranch)
                    .build();

            Set<ExportDetailsEntity> detailsSet = new HashSet<>();
            for (ExportDetailsRequest details : request.exportDetails()) {
                ExportDetailsEntity detailsEntity = exportDetailsService.create(details);//Validate product,color,size
                detailsEntity.setExportRecord(exportEntity);
                detailsSet.add(detailsEntity);
            }

            exportEntity.setExportDetails(detailsSet);
            ExportEntity exportSaved = exportRepository.save(exportEntity);

            return ResponseErrorTemplate.builder()
                    .message("import successful")
                    .code("201")
                    .object(exportMapper.toExportDTO(exportSaved))
                    .build();

        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }

    @Override
    public ResponseErrorTemplate verifyExport(Long id) throws Exception {
        log.info("invoke verify export.....................");
        try {
            ExportEntity exportEntity = getExportById(id);
            List<ExportDetailsEntity> exportDetails = exportEntity.getExportDetails().stream().toList();
            BranchEntity fromBranch = exportEntity.getFromBranch();
            BranchEntity toBranch = exportEntity.getToBranch();
            List<StockEntity> stocksToUpdate = mapStockQuantity(fromBranch, toBranch, exportDetails);
            stockRepository.saveAll(stocksToUpdate);
            return ResponseErrorTemplate.builder()
                    .code("200")
                    .message("Verify Stock successful")
                    .build();
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }

    }

    @Override
    public ResponseErrorTemplate getExport(Long id) throws Exception {
        ExportEntity exportEntity = getExportById(id);
        return ResponseErrorTemplate.builder()
                .object(exportMapper.toExportDTO(exportEntity))
                .code("200")
                .message("successful")
                .build();
    }

    @Override
    public ResponseErrorTemplate deleteExport(Long id) throws Exception {
        try {
            ExportEntity exportData = this.getExportById(id);
            this.exportRepository.delete(exportData);
            return ResponseErrorTemplate.builder()
                    .message("import was Drop successful")
                    .code("200")
                    .object("{}")
                    .build();
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }

    }

    private ExportEntity getExportById(Long id) throws Exception {
        return exportRepository.findById(id).orElseThrow(() -> new CustomMessageException("Export not founded", "404"));
    }

    private List<StockEntity> mapStockQuantity(BranchEntity fromBranch, BranchEntity toBranch, List<ExportDetailsEntity> exportDetails) throws Exception {
        try {
            List<StockEntity> updateStock = new ArrayList<>();
            for (ExportDetailsEntity detail : exportDetails) {
                StockEntity fromStockEntity = stockRepository.getStock(fromBranch, detail.getProduct(), detail.getSize(), detail.getColor());
                StockEntity toStockEntity = stockRepository.getStock(toBranch, detail.getProduct(), detail.getSize(), detail.getColor());

                fromStockEntity.setQuantity(fromStockEntity.getQuantity() - detail.getQuantity());
                toStockEntity.setQuantity(toStockEntity.getQuantity() + detail.getQuantity());
                updateStock.addAll(List.of(fromStockEntity, toStockEntity));
            }
            return updateStock;
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }

    }

}
