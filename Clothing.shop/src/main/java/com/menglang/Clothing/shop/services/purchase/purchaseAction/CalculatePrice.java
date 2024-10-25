package com.menglang.Clothing.shop.services.purchase.purchaseAction;

import com.menglang.Clothing.shop.dto.discount.ItemCalculateType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CalculatePrice {

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


}
