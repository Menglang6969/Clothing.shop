package com.menglang.Clothing.shop.services.order;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.discount.DiscountMapper;
import com.menglang.Clothing.shop.dto.order.OrderMapper;
import com.menglang.Clothing.shop.dto.order.OrderRequest;
import com.menglang.Clothing.shop.dto.order.OrderResponse;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsRequest;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderMapper;
import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.entity.enums.PurchaseStatus;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.helpers.GetEntitiesById;
import com.menglang.Clothing.shop.repositories.OrderItemRepository;
import com.menglang.Clothing.shop.repositories.OrderRepository;
import com.menglang.Clothing.shop.repositories.PurchaseItemsRepository;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
import com.menglang.Clothing.shop.services.customer.CustomerServiceImpl;
import com.menglang.Clothing.shop.services.order.OrderAction.OrderCheck;
import com.menglang.Clothing.shop.services.purchase.purchaseAction.CalculatePrice;
import com.menglang.Clothing.shop.services.purchase.purchaseAction.PurchaseCheck;
import com.menglang.Clothing.shop.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    @Autowired
    private final GetEntitiesById getEntity;
    @Autowired
    private final CalculatePrice calculatePrice;
    @Autowired
    private final PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private OrderCheck orderCheck;
    @Autowired
    private final CustomerServiceImpl customerService;
    @Autowired
    private final DiscountMapper discountMapper;
    @Autowired
    private final OrderMapper orderMapper;


    @Override
    @Transactional
    public ResponseTemplate findOrderById(Long id) throws Exception {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Purchase Order Not Found"));

        List<OrderDetailsRequest> orderDetailsRequests=new ArrayList<>();

        for(PurchaseItemEntity item:purchaseOrder.getPurchaseItems()){
            OrderDetailsRequest orderDetails=OrderDetailsRequest.builder()
                    .color(item.getColor().getId())
                    .size(item.getSize().getId())
                    .price(item.getPrice())
                    .productId(item.getProduct().getId())
                    .discountedPercent(item.getDiscountedPercent())
                    .discountedPrice(item.getDiscountedPrice())
                    .quantity(item.getQuantity())
                    .build();
            orderDetailsRequests.add(orderDetails);
        }
        validateProductPrice(orderDetailsRequests);

        OrderRequest orderRequest=OrderRequest.builder()
                .customerType(purchaseOrder.getCustomerType())
                .branch(purchaseOrder.getBranch().getId())
                .customer(purchaseOrder.getCustomer().getId())
                .discountedPrice(purchaseOrder.getTotalDiscountedPrice())
                .discountedPercent(purchaseOrder.getTotalDiscountedPercent())
                .address("")
                .items(orderDetailsRequests)
                .build();
        purchaseOrder.setPurchaseStatus(PurchaseStatus.SUCCESS);
        purchaseOrderRepository.save(purchaseOrder);
        return this.makeOrder(orderRequest);
    }

    @Override
    public BaseResponse findByOrderNo(String orderNo) throws Exception {
        return orderRepository.findByOrderNoContainingIgnoreCase(orderNo);
    }

    @Override
    @Transactional
    public ResponseTemplate makeOrder(OrderRequest data) throws Exception {
       try {
           log.info("invoke create order..............");
           OrderEntity newOrder = new OrderEntity();
           validateProductPrice(data.items());
           CustomerTypeResponse customerRes = customerService.checkCustomerType(
                   data.customerType(),
                   data.customer(),
                   data.generalCustomer()
           );
           newOrder.setOrderNo(data.orderNo());//to-do generate ORD+AutoIncrement
           newOrder.setAddress(data.address());
           newOrder.setBranch(getEntity.findBranchById(data.branch()));
           newOrder.setCustomer(customerRes.getCustomer());
           newOrder.setGeneralCustomer(customerRes.getGeneralCustomer());
           newOrder.setCustomerType(data.customerType());
           newOrder.setDiscountedPercent(data.discountedPercent());
           newOrder.setDiscountedPrice(data.discountedPrice());

           Set<OrderItemsEntity> itemsDetails=orderCheck.getItemsOrdered(data.items(),newOrder);
           newOrder.setTotalItem(itemsDetails.size());
           double totalPrice = calculatePrice.calculateTotalPrice(discountMapper.OrderToCalculateType(itemsDetails));
           totalPrice = calculatePrice.calculateDiscountPrice(totalPrice, newOrder.getDiscountedPercent(), newOrder.getDiscountedPrice());
           newOrder.setOrderItems(itemsDetails);
           newOrder.setTotalPrice(totalPrice);
           newOrder.setTotalPriceKHR(totalPrice*4000);


           OrderEntity saveOrder = orderRepository.save(newOrder);
           Set<OrderItemsEntity> items = newOrder.getOrderItems();
           orderItemRepository.saveAll(items);
           return ResponseTemplate.builder()
                   .object(orderMapper.toOrderDto(saveOrder))
                   .code("201")
                   .message("created order successful")
                   .build();

       }catch (Exception e){
           throw new BadRequestException(e.getMessage());
       }
    }

    @Override
    public Page<OrderEntity> findAll(int page, int limit, String sort, Long branch, Date startDate, Date endDate) throws Exception {
        Pageable pageable= PageableResponse.mapPageable(page,limit,sort);
        BranchEntity branchEntity=null;
        if(branch!=null){
            branchEntity=getEntity.findBranchById(branch);
        }
        return this.orderRepository.findAllByBranchAndCreatedAtBetween(branchEntity,startDate,endDate,pageable);
    }

    private void validateProductPrice(List<OrderDetailsRequest> orderItems) throws Exception {
        for(OrderDetailsRequest request:orderItems){
            ProductEntity existProduct=getEntity.findProductById(request.productId());
            if(!Objects.equals(existProduct.getSellCost(), request.price())){
                throw new CustomMessageException("Product Price Is Missing"+existProduct.getTitle(), "404");
            }
        }
    }

    private String generateOrderNo(){
        return "ORD"+Math.round(Math.random()*100000);
    }

}
