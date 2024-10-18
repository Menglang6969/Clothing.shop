package com.menglang.Clothing.shop.services.stock;

import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.StockEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.ProductRepository;
import com.menglang.Clothing.shop.repositories.StockRepository;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    @Autowired
    private final StockRepository stockRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductServiceImpl productService;


    @Override
    @Transactional
    public StockEntity updateStock(Long id,StockEntity newStock) throws Exception {

        log.info("invoke update Stock...........................{}",newStock.getProduct().getId());

        try{
            ProductEntity product = productService.findProductById(newStock.getProduct().getId());
            StockEntity curStock = this.findById(id);

            Double baseCost = calculateAgvCost(
                    Double.parseDouble(product.getBaseCost().toString()),
                    Double.parseDouble(newStock.getProduct().getBaseCost().toString()),
                    curStock.getQuantity(),
                    newStock.getQuantity()
            );
            log.info("Calculate Price: {}",baseCost);
            product.setBaseCost(BigDecimal.valueOf(baseCost));
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


    private Double calculateAgvCost(double currentCost, double newCost, int currentQty, int newQty) {
        return ((currentCost * currentQty) + (newCost * newQty)) / (currentQty + newQty);
    }

    private StockEntity findById(Long id) throws Exception {
        return stockRepository.findById(id).orElseThrow(() -> new CustomMessageException("Product Stock Not found", "400"));
    }

}
