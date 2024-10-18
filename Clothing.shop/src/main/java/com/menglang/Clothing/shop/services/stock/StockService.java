package com.menglang.Clothing.shop.services.stock;

import com.menglang.Clothing.shop.entity.StockEntity;

public interface StockService {

    public StockEntity updateStock(Long id,StockEntity data) throws Exception;
}
