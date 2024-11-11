package com.menglang.Clothing.shop.services.purchase.purchaseAction;

import com.menglang.Clothing.shop.dto.discount.ItemCalculateType;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsRequest;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CalculatePrice {
    @Autowired
    private final GetEntitiesById getEntity;

    private static final Logger log = LoggerFactory.getLogger(CalculatePrice.class);

    public<T extends ItemCalculateType> Double calculateTotalPrice(Set<T> items) {
        double totalPrice = 0.0;
        for (T item : items) {
            totalPrice += calculateDiscountWithQty(
                    item.getPrice(),
                    item.getQuantity(),
                    item.getDiscountedPercent(),
                    item.getDiscountedPrice());
        }
        log.info(" calculate price .........................{}", totalPrice);
        return totalPrice;
    }

    public double calculateDiscountWithQty(double price, int qty, int discountedPercent, double discountedPrice) {
        return calculateDiscountPrice(price, discountedPercent, discountedPrice) * qty;
    }

    public double calculateDiscountPrice(double price, int discountedPercent, double discountedPrice) {
        price = discountedPrice != 0.0 ? price - discountedPrice : price;
        price -= (price * discountedPercent) / 100.0;
        log.info(" price: {}", price);
        return price;
    }

    public double calculateTotalBaseCost(List<OrderDetailsRequest> allItems) throws Exception {
        double totalBaseCost=0;
        for (OrderDetailsRequest item:allItems){
            ProductEntity product=getEntity.findProductById(item.productId());
            double sumCost=product.getBaseCost()*item.quantity();
            totalBaseCost+=sumCost;
        }
        return totalBaseCost;

    }
}
