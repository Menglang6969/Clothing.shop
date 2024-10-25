package com.menglang.Clothing.shop.services.purchase.purchaseAction;

import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
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
public class PurchaseCheck {

    private static final Logger log = LoggerFactory.getLogger(PurchaseCheck.class);
    @Autowired
    private final PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private final GetEntitiesById getEntity;


    public Set<PurchaseItemEntity> getItemsPurchase(List<ItemRequest> data, PurchaseOrderEntity purchaseOrder) throws Exception {
        log.info("invoke getItems purchase................");
        List<PurchaseItemEntity> order_items = new ArrayList<>();
        for (ItemRequest item : data) {
            PurchaseItemEntity order_item = validatePurchaseItems(item, purchaseOrder);
            log.info("...color_id {}, validate {} ", item.color(), order_item.getColor().getName());
            order_items.add(order_item);
        }
        for (PurchaseItemEntity item : order_items) {
            log.info("-----before response item colorId: {} :{}", item.getColor().getId(), item.getColor().getName());
        }
        return new HashSet<>(order_items);
    }


    public PurchaseItemEntity validatePurchaseItems(ItemRequest item, PurchaseOrderEntity purchaseOrder) throws Exception {
        log.info(" validate purchase Item..........................{}", item.color());

        try {
            ProductEntity product = getEntity.findProductById(item.productId());
            ColorEntity color = getEntity.findColorById(item.color());
            SizeEntity size = getEntity.findSizeById(item.size());
            log.info(" get color Item..........................{}:{}", color.getId(), color.getName());
            return PurchaseItemEntity.builder()
                    .color(color)
                    .size(size)
                    .price(item.price())
                    .discountedPrice((double) item.discountedPrice())
                    .discountedPercent(item.discountedPercent())
                    .quantity(item.quantity())
                    .purchase(purchaseOrder)
                    .product(product)
                    .build();

        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }


    public PurchaseOrderEntity findById(Long id) throws Exception {
        return this.purchaseOrderRepository.findById(id).orElseThrow(() -> new CustomMessageException("Purchase Order Not founded", "400"));
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
