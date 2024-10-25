package com.menglang.Clothing.shop.services.purchase.purchase;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderMapper;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.entity.base.BaseEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.entity.enums.PurchaseStatus;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.CustomerRepository;
import com.menglang.Clothing.shop.repositories.PurchaseItemsRepository;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
    @Autowired
    private final PurchaseItemsRepository purchaseItemsRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final GetEntitiesById getEntity;
    @Autowired
    private final PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    @Override
    @Transactional()
    public ResponseTemplate createPurchase(PurchaseOrderRequest data) throws Exception {
        log.info("invoke create purchase..............");
        PurchaseOrderEntity purchaseOrder = extractPurchaseItems(data);

        PurchaseOrderEntity savedPurchase = purchaseOrderRepository.save(purchaseOrder);
        Set<PurchaseItemEntity> items = purchaseOrder.getPurchaseItems();
        purchaseItemsRepository.saveAll(items);
        return ResponseTemplate.builder()
                .object(purchaseOrderMapper.toPurchaseOrderDTO(savedPurchase))
                .code("201")
                .message("created purchase successful")
                .build();
    }

    @Override
    @Transactional
    public ResponseTemplate editPurchase(Long id, PurchaseOrderRequest data) throws Exception {
       try{
           log.info("invoke update purchase...................");
           PurchaseOrderEntity updatePurchase = this.findById(id);
           CustomerTypeResponse customerRes=checkCustomerType(data.customerType(),data.customer(),data.generalCustomer());
           updatePurchase.setCustomer(customerRes.getCustomer());
           updatePurchase.setBranch(getEntity.findBranchById(data.branch()));
           updatePurchase.setGeneralCustomer(customerRes.getGeneralCustomer());
           updatePurchase.setTotalDiscountedPrice(data.discountedPrice());
           updatePurchase.setTotalDiscountedPercent(data.discountedPercent());

           updatePurchase.getPurchaseItems().clear();

           Set<PurchaseItemEntity> itemEntitySet = getItemsPurchase(data.items(), updatePurchase);

           updatePurchase.setTotalItem(itemEntitySet.size());
           double totalPrice = calculateTotalPrice(itemEntitySet);
           totalPrice = calculateDiscountPrice(totalPrice, data.discountedPercent(), data.discountedPrice());
           updatePurchase.getPurchaseItems().addAll(itemEntitySet);
           updatePurchase.setTotalPrice(totalPrice);

           PurchaseOrderEntity savedPurchase = purchaseOrderRepository.save(updatePurchase);
           for(PurchaseItemEntity item:getItemsPurchase(data.items(), updatePurchase)){
               assert item != null;
               log.info("-----item loop: {} :{}", item.getSize().getId()+":"+item.getSize().getName(),item.getColor().getName()+":"+item.getColor().getName());
           }

//           purchaseItemsRepository.saveAll(getItemsPurchase(data.items(), updatePurchase));
           return ResponseTemplate.builder()
                   .object(purchaseOrderMapper.toPurchaseOrderDTO(savedPurchase))
                   .code("201")
                   .message("update purchase successful")
                   .build();
       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"400");
       }
    }

    @Override
    public ResponseTemplate dropPurchase(Long id) throws Exception {
        return null;
    }


    private PurchaseOrderEntity extractPurchaseItems(PurchaseOrderRequest data) throws Exception {

        log.info("invoke exact........................ ");
        PurchaseOrderEntity purchaseOrder = new PurchaseOrderEntity();

        try {
            CustomerTypeResponse customerRes=checkCustomerType(data.customerType(),data.customer(),data.generalCustomer());
            purchaseOrder.setBranch(getEntity.findBranchById(data.branch()));
            purchaseOrder.setCustomer(customerRes.getCustomer());
            purchaseOrder.setGeneralCustomer(customerRes.getGeneralCustomer());

            purchaseOrder.setCustomerType(data.customerType());
            purchaseOrder.setTotalDiscountedPercent(data.discountedPercent());
            purchaseOrder.setTotalDiscountedPrice(data.discountedPrice());
            purchaseOrder.setPurchaseStatus(PurchaseStatus.IN_PROGRESS);
            Set<PurchaseItemEntity> itemEntitySet = getItemsPurchase(data.items(), purchaseOrder);
            purchaseOrder.setTotalItem(itemEntitySet.size());
            double totalPrice = calculateTotalPrice(itemEntitySet);
            totalPrice = calculateDiscountPrice(totalPrice, purchaseOrder.getTotalDiscountedPercent(), purchaseOrder.getTotalDiscountedPrice());
            purchaseOrder.setPurchaseItems(itemEntitySet);
            purchaseOrder.setTotalPrice(totalPrice);

            return purchaseOrder;
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }

    }


    private Set<PurchaseItemEntity> getItemsPurchase(List<ItemRequest> data, PurchaseOrderEntity purchaseOrder) throws Exception {
        log.info("invoke getItems purchase................");
        List<PurchaseItemEntity> order_items = new ArrayList<>();
        for (ItemRequest item : data) {
            PurchaseItemEntity order_item = validatePurchaseItems(item, purchaseOrder);
            log.info("...color_id {}, validate {} ", item.color(),order_item.getColor().getName());
            order_items.add(order_item);
        }
        for(PurchaseItemEntity item:order_items){
            log.info("-----before response item colorId: {} :{}",item.getColor().getId(),item.getColor().getName());
        }
        return new HashSet<>(order_items);
    }


    private PurchaseItemEntity validatePurchaseItems(ItemRequest item, PurchaseOrderEntity purchaseOrder) throws Exception {
        log.info(" validate purchase Item..........................{}",item.color());
        try {
            ProductEntity product = getEntity.findProductById(item.productId());
            ColorEntity color = getEntity.findColorById(item.color());
            SizeEntity size = getEntity.findSizeById(item.size());
            log.info(" get color Item..........................{}:{}",color.getId(),color.getName());
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

    private Double calculateTotalPrice(Set<PurchaseItemEntity> items) {
        log.info(" calculate price .........................");
        double totalPrice = 0.0;
        for (PurchaseItemEntity item : items) {
            totalPrice += calculateDiscountWithQty(
                    item.getPrice(),
                    item.getQuantity(),
                    item.getDiscountedPercent(),
                    item.getDiscountedPrice());
        }
        log.info(" calculate price .........................{}", totalPrice);
        return totalPrice;
    }


    private CustomerEntity findCustomerById(Long customerId) throws Exception {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomMessageException("Customer Not found", "400"));
    }

    private double calculateDiscountPrice(double price, int discountedPercent, double discountedPrice) {
        // Apply discounted price if not null
        price = discountedPrice != 0.0 ? price - discountedPrice : price;
        // Apply discount percentage if not null
        price -= (price * discountedPercent) / 100.0;
        log.info(" price: {}", price);
        return price;
    }

    private double calculateDiscountWithQty(double price, int qty, int discountedPercent, double discountedPrice) {
        return calculateDiscountPrice(price, discountedPercent, discountedPrice) * qty;
    }

    private PurchaseOrderEntity findById(Long id) throws Exception {
        return this.purchaseOrderRepository.findById(id).orElseThrow(() -> new CustomMessageException("Purchase Order Not founded", "400"));
    }

    private CustomerTypeResponse checkCustomerType(CustomerType type,Long cid,String generalCustomer) throws Exception{
        CustomerEntity customer = null;
        String general_customer =generalCustomer;
        if (type.equals(CustomerType.SPECIAL)) {
            customer = findCustomerById(cid);
            general_customer = null;
        }
        return CustomerTypeResponse.builder()
                .generalCustomer(generalCustomer)
                .customer(customer)
                .customerType(type)
                .build();
    }
}
