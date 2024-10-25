package com.menglang.Clothing.shop.services.order;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.order.OrderRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderMapper;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.OrderRepository;
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

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private final OrderRepository orderRepository;
    private final PurchaseItemsRepository purchaseItemsRepository;
    @Autowired
    private final GetEntitiesById getEntity;
    @Autowired
    private final PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private final CalculatePrice calculatePrice;
    @Autowired
    private final PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PurchaseCheck purchaseCheck;

    @Override
    public ResponseTemplate findOrderById(Long id) throws Exception {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(() -> new CustomMessageException("Purchase Order Not Found", "404"));

        return null;
    }

    @Override
    @Transactional
    public ResponseTemplate makeOrder(OrderRequest data) throws Exception {
        log.info("invoke create purchase..............");
        OrderEntity newOrder = new OrderEntity();
        CustomerTypeResponse customerRes = purchaseCheck.checkCustomerType(
                data.customerType(),
                data.customer(),
                data.generalCustomer()
        );
        newOrder.setAddress(data.address());
        newOrder.setBranch(getEntity.findBranchById(data.branch()));
        newOrder.setCustomer(customerRes.getCustomer());
        newOrder.setGeneralCustomer(customerRes.getGeneralCustomer());
        newOrder.setCustomerType(data.customerType());
        newOrder.setDiscountedPercent(data.discountedPercent());
        newOrder.setDiscountedPrice(data.discountedPrice());


        return null;
    }


}
