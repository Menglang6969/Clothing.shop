package com.menglang.Clothing.shop.services.stock;

import com.menglang.Clothing.shop.dto.stock.StockResponse;
import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import com.menglang.Clothing.shop.entity.StockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface StockService {

    Page<StockEntity> getAll(int page,int limit,String sort,String query,Long branch_id) throws Exception;
    public StockEntity updateStock(Long id,StockEntity data,double importCost) throws Exception;
    public void addProductStocks(Set<ColorEntity> colors, Set<SizeEntity> sizes, ProductEntity product) throws Exception;
    public void exportProducts(Long fromBranch,Long toBranch,StockEntity data) throws Exception;
    public Page<StockResponse> getCustomPage( List<StockResponse> stock,Pageable pageable, Long totalElements) throws Exception;
}
