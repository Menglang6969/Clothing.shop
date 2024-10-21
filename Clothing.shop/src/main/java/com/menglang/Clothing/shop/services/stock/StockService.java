package com.menglang.Clothing.shop.services.stock;

import com.menglang.Clothing.shop.entity.ColorEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.SizeEntity;
import com.menglang.Clothing.shop.entity.StockEntity;

import java.util.Set;

public interface StockService {

    public StockEntity updateStock(Long id,StockEntity data,double importCost) throws Exception;
    public void addProductStocks(Set<ColorEntity> colors, Set<SizeEntity> sizes, ProductEntity product) throws Exception;
}
