package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.dto.pageResponse.page.PageResponse;
import com.menglang.Clothing.shop.dto.stock.StockDTO;
import com.menglang.Clothing.shop.dto.stock.StockResponse;
import com.menglang.Clothing.shop.entity.StockEntity;
import com.menglang.Clothing.shop.services.stock.StockService;
import com.menglang.Clothing.shop.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/stock")
@RequiredArgsConstructor
public class StockController {

    private static final Logger log = LoggerFactory.getLogger(StockController.class);
    @Autowired
    private final StockService stockService;


    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAllStocks(
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "5", required = false) int limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "branch",required = true,defaultValue = "1") Long branch
    ) throws Exception {
        Page<StockEntity> stockEntities = this.stockService.getAll(page, limit, sort, query,branch);
        List<StockEntity> listStock = stockEntities.stream().toList();
        StockDTO stockDTO=new StockDTO();
        List<StockResponse> stockResponseList = stockDTO.mapToStockDTO(listStock);
        Pageable pageable = PageableResponse.mapPageable(page, limit, sort);
        //custom TotalElements
        Page<StockResponse> pageImp=this.stockService.getCustomPage(stockResponseList,pageable,(long)stockResponseList.size());
        return BaseResponse.success(stockResponseList, pageImp, "success");
    }
}
