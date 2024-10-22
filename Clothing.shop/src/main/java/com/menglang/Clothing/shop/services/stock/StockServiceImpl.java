package com.menglang.Clothing.shop.services.stock;

import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.BranchRepository;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.repositories.StockRepository;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    @Autowired
    private final StockRepository stockRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final BranchRepository branchRepository;


    @Override
    @Transactional
    public StockEntity updateStock(Long id,StockEntity newStock,double newCost) throws Exception {

        try{
            ProductEntity product = productRepository.findById(newStock.getProduct().getId()).orElseThrow(()->new CustomMessageException("Product Not found","400"));
            StockEntity curStock=this.stockRepository.getStock(
                    newStock.getBranch(),
                    newStock.getProduct(),
                    newStock.getSize(),
                    newStock.getColor()
            );

            Long currentQtyProduct=this.stockRepository.getCurrentQtyProduct(product);
            currentQtyProduct=currentQtyProduct!=null?currentQtyProduct:0L;

            log.info("Stock: {}",currentQtyProduct);
            log.info(" current stock {}",curStock.getBranch().getName());

            Double baseCost = calculateAgvCost(
                    Double.parseDouble(product.getBaseCost().toString()),
                    Integer.parseInt(currentQtyProduct.toString()),
                    newCost,
                    newStock.getQuantity()
            );
            log.info("Calculate Price: {}, ",baseCost);
            product.setBaseCost(baseCost);
            ProductEntity updatedProduct = productRepository.save(product);
            curStock.setBranch(newStock.getBranch());
            curStock.setSize(newStock.getSize());
            curStock.setProduct(updatedProduct);
            curStock.setQuantity(curStock.getQuantity() + newStock.getQuantity());

            return stockRepository.save(curStock);
        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"400");
        }

    }

    @Override
    public void addProductStocks(Set<ColorEntity> colors, Set<SizeEntity> sizes, ProductEntity product) throws Exception {
        List<BranchEntity> branchEntities=branchRepository.findAll();
        List<StockEntity> stockEntities=new ArrayList<>();
        try{
            if((long) branchEntities.size() >0){
                for(BranchEntity branch:branchEntities){
                    for(ColorEntity color:colors){
                        for(SizeEntity size:sizes){
                            StockEntity stock=StockEntity.builder()
                                    .branch(branch)
                                    .product(product)
                                    .color(color)
                                    .size(size)
                                    .quantity(0)
                                    .build();
                            stockEntities.add(stock);
                        }
                    }
                }
            }
            stockRepository.saveAll(stockEntities);
        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"400");
        }
    }


    @Override
    public void exportProducts(Long fromBranch, Long toBranch, StockEntity data) throws Exception {

    }


    private Double calculateAgvCost(double currentCost,int currentQty, double newCost, int newQty) {
        log.info(" cur {} new {} tot_qty {} sum_:{}",currentCost*currentQty,newCost*newQty,currentQty+newQty,(currentCost*currentQty)+(newCost*newQty));
        return ((currentCost * currentQty) + (newCost * newQty)) / (currentQty + newQty);
    }

    private StockEntity findById(Long id) throws Exception {
        return stockRepository.findById(id).orElseThrow(() -> new CustomMessageException("Product Stock Not found", "400"));
    }

}
