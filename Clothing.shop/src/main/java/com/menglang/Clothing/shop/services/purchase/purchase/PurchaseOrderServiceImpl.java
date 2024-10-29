package com.menglang.Clothing.shop.services.purchase.purchase;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.discount.DiscountMapper;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderMapper;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.entity.enums.PurchaseStatus;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.PurchaseItemsRepository;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
import com.menglang.Clothing.shop.services.purchase.purchaseAction.CalculatePrice;
import com.menglang.Clothing.shop.services.purchase.purchaseAction.PurchaseCheck;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);
    @Autowired
    private final PurchaseItemsRepository purchaseItemsRepository;
    @Autowired
    private final GetEntitiesById getEntity;
    @Autowired
    private final PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private final CalculatePrice calculatePrice;
    @Autowired
    private PurchaseCheck purchaseCheck;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private DiscountMapper discountMapper;

    @Override
    @Transactional()
    public ResponseTemplate createPurchase(PurchaseOrderRequest data) throws Exception {
        log.info("invoke create purchase..............");
        PurchaseOrderEntity purchaseOrder = new PurchaseOrderEntity();
        CustomerTypeResponse customerRes = purchaseCheck.checkCustomerType(data.customerType(), data.customer(), data.generalCustomer());
        purchaseOrder.setBranch(getEntity.findBranchById(data.branch()));
        purchaseOrder.setCustomer(customerRes.getCustomer());
        purchaseOrder.setGeneralCustomer(customerRes.getGeneralCustomer());
        purchaseOrder.setCustomerType(data.customerType());
        purchaseOrder.setTotalDiscountedPercent(data.discountedPercent());
        purchaseOrder.setTotalDiscountedPrice(data.discountedPrice());
        purchaseOrder.setPurchaseStatus(PurchaseStatus.IN_PROGRESS);

        Set<PurchaseItemEntity> itemEntitySet = purchaseCheck.getItemsPurchase(data.items(), purchaseOrder);
        purchaseOrder.setTotalItem(itemEntitySet.size());
        double totalPrice = calculatePrice.calculateTotalPrice(discountMapper.PurchaseToCalculateType(itemEntitySet));
        totalPrice = calculatePrice.calculateDiscountPrice(totalPrice, purchaseOrder.getTotalDiscountedPercent(), purchaseOrder.getTotalDiscountedPrice());
        purchaseOrder.setPurchaseItems(itemEntitySet);
        purchaseOrder.setTotalPrice(totalPrice);

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
        try {
            log.info("invoke update purchase...................");
            PurchaseOrderEntity updatePurchase = purchaseCheck.findById(id);
            CustomerTypeResponse customerRes = purchaseCheck.checkCustomerType(data.customerType(), data.customer(), data.generalCustomer());
            updatePurchase.setCustomer(customerRes.getCustomer());
            updatePurchase.setBranch(getEntity.findBranchById(data.branch()));
            updatePurchase.setGeneralCustomer(customerRes.getGeneralCustomer());
            updatePurchase.setTotalDiscountedPrice(data.discountedPrice());
            updatePurchase.setTotalDiscountedPercent(data.discountedPercent());

            //clear current data then add new items not update existing item
            updatePurchase.getPurchaseItems().clear();

            Set<PurchaseItemEntity> itemEntitySet = purchaseCheck.getItemsPurchase(data.items(), updatePurchase);

            updatePurchase.setTotalItem(itemEntitySet.size());
            double totalPrice = calculatePrice.calculateTotalPrice(discountMapper.PurchaseToCalculateType(itemEntitySet));
            totalPrice = calculatePrice.calculateDiscountPrice(totalPrice, data.discountedPercent(), data.discountedPrice());
            updatePurchase.getPurchaseItems().addAll(itemEntitySet);
            updatePurchase.setTotalPrice(totalPrice);

            PurchaseOrderEntity savedPurchase = purchaseOrderRepository.save(updatePurchase);

            return ResponseTemplate.builder()
                    .object(purchaseOrderMapper.toPurchaseOrderDTO(savedPurchase))
                    .code("201")
                    .message("update purchase successful")
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseTemplate dropPurchase(Long id) throws Exception {
        PurchaseOrderEntity purchaseOrder = purchaseCheck.findById(id);
        purchaseOrder.setPurchaseStatus(PurchaseStatus.DROP);
        this.purchaseOrderRepository.save(purchaseOrder);
        return ResponseTemplate.builder()
                .code("200")
                .message("Purchase Order was Drop")
                .object(this.purchaseOrderMapper.toPurchaseOrderDTO(purchaseOrder))
                .build();
    }


}
