package com.menglang.Clothing.shop.services.order.OrderAction;

import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsRequest;
import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.services.purchase.purchaseAction.PurchaseCheck;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderCheck {
    private static final Logger log = LoggerFactory.getLogger(PurchaseCheck.class);
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final GetEntitiesById getEntity;


    public Set<OrderItemsEntity> getItemsOrdered(List<OrderDetailsRequest> data, OrderEntity order) throws Exception {
        log.info("invoke orderItem ................");
        List<OrderItemsEntity> order_items = new ArrayList<>();
        for (OrderDetailsRequest item : data) {
            OrderItemsEntity order_item = validateOrderItems(item, order);
            order_items.add(order_item);
        }

        return new HashSet<>(order_items);
    }

    public OrderItemsEntity validateOrderItems(OrderDetailsRequest item, OrderEntity order) throws Exception {
        log.info(" validate order Item..........................{} {} {}", item.color(),item.size(),item.productId());

        try {
            ProductEntity product = getEntity.findProductById(item.productId());
            ColorEntity color = getEntity.findColorById(item.color());
            SizeEntity size = getEntity.findSizeById(item.size());
            log.info(" get color Item..........................{}:{}", color.getId(), color.getName());
            return OrderItemsEntity.builder()
                    .color(color)
                    .size(size)
                    .price(item.price())
                    .discountedPrice((double) item.discountedPrice())
                    .discountedPercent(item.discountedPercent())
                    .quantity(item.quantity())
                    .order(order)
                    .product(product)
                    .build();

        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }

    public CustomerTypeResponse checkCustomerType(CustomerType type, Long cid, String generalCustomer) throws Exception {
        CustomerEntity customer = null;
        String general_customer = generalCustomer;
        if (type.equals(CustomerType.SPECIAL)) {
            customer = getEntity.findCustomerById(cid);
            general_customer = null;
        }
        return CustomerTypeResponse.builder()
                .generalCustomer(generalCustomer)
                .customer(customer)
                .customerType(type)
                .build();
    }

}
