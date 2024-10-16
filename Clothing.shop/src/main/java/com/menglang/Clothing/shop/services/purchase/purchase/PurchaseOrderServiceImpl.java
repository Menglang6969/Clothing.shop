package com.menglang.Clothing.shop.services.purchase.purchase;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemRequest;
import com.menglang.Clothing.shop.dto.purchase.purchaseOrder.PurchaseOrderRequest;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.ProductEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.CustomerRepository;
import com.menglang.Clothing.shop.repositories.PurchaseItemsRepository;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import com.menglang.Clothing.shop.services.purchase.purchaseItems.PurchaseItemsService;
import lombok.RequiredArgsConstructor;
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

    @Autowired
    private final PurchaseItemsRepository purchaseItemsRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PurchaseItemsService purchaseItemsService;
    @Autowired
    private ProductServiceImpl productService;

    @Override
    @Transactional()
    public ResponseErrorTemplate createPurchase(PurchaseOrderRequest data) throws Exception {
        PurchaseOrderEntity purchaseOrder=extractPurchaseItems(data);
        return null;
    }

    @Override
    public String editPurchase(Long userId, ItemRequest request) throws Exception {
//        PurchaseOrderEntity cart = purchaseOrderRepository.findByUserId(userId);
        //      ProductEntity product = productService.findProductById(request.productId());
//        PurchaseItemEntity isPresent = purchaseItemsService.isCartItemExist(cart, product, request.size());
//
//        if (isPresent == null) {
//            PurchaseItemEntity cartItem = PurchaseItemEntity.builder()
//                    .product(product)
//                    .items(cart)
//                    .quantity(request.quantity())
//                    .price(Double.valueOf(request.price()))
//                    .size(request.size())
//                    .build();
//
//            PurchaseItemEntity createdCartItem = purchaseItemsService.addItem(cartItem);
//            cart.getCartItems().add(createdCartItem);//add new item to previous cartItems of carts
//        }
        return "Item Add to Cart";
    }

    @Override
    public PurchaseOrderEntity findUserCart(Long userId) throws Exception {
//        try {
//            PurchaseOrderEntity cart = purchaseOrderRepository.findByUserId(userId);
//            double totalPrice = (double) 0;
//            int totalDiscountPrice = 0;
//            int totalItem = 0;
//
//
//            for (PurchaseItemEntity cartItem : cart.getCartItems()) {
//                totalPrice += cartItem.getPrice() * cartItem.getQuantity();
//                totalDiscountPrice += cartItem.getDiscountedPrice();
//                totalItem += cartItem.getQuantity();
//            }
//
//            cart.setDiscount(totalDiscountPrice);
//            cart.setTotalDiscountedPrice(totalDiscountPrice);
//            cart.setTotalItem(totalItem);
//            cart.setTotalPrice(totalPrice);
//
//            return purchaseOrderRepository.save(cart);
//        } catch (Exception e) {
//            throw new CustomMessageException(e.getMessage(), "500");
//        }
        return null;

    }

    private PurchaseOrderEntity extractPurchaseItems(PurchaseOrderRequest data) throws Exception {
        Set<PurchaseItemEntity> itemEntitySet = getItemsPurchase(data.items());

        CustomerEntity customer = null;
        String general_customer = data.generalCustomer();
        if (data.customerType().equals(CustomerType.SPECIAL)) {
            customer = findCustomerById(data.customer());
            general_customer = null;
        }



        //To-do calculate total price
        PurchaseOrderEntity purchaseOrder = PurchaseOrderEntity.builder()
                .generalCustomer(data.generalCustomer())
                .purchaseItems(itemEntitySet)
                .discount(data.discount())
                .customerType(data.customerType())
                .customer(customer)
                .generalCustomer(general_customer)
                .discount(data.discount())
                .totalItem(1)
                .totalDiscountedPrice(1)
                .totalPrice(1)
                .build();
        return purchaseOrder;
    }

    private PurchaseItemEntity validatePurchaseItems(ItemRequest item) throws Exception {
        try {
            ProductEntity product = validateProductInfo(item);
            return PurchaseItemEntity.builder().color(item.color()).size(item.size()).price(item.price()).discountedPrice(item.discountedPrice()).discounted_percent(item.discountedPercent()).quantity(item.quantity()).product(product).build();

        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }

    private ProductEntity validateProductInfo(ItemRequest item) throws Exception {
        try {
            ProductEntity product = productService.findProductById(item.productId());
            // To do validate price & quantity
            return null;
        } catch (Exception e) {
            throw new CustomMessageException(e.getMessage(), "400");
        }
    }

    private CustomerEntity findCustomerById(Long customerId) throws Exception {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomMessageException("Customer Not found", "400"));
    }

    private Set<PurchaseItemEntity> getItemsPurchase(List<ItemRequest> data) throws Exception {
        List<PurchaseItemEntity> order_items = new ArrayList<>();
        for (ItemRequest item : data) {
            PurchaseItemEntity order_item = validatePurchaseItems(item);
            order_items.add(order_item);
        }
        return new HashSet<>(purchaseItemsRepository.saveAll(order_items));
    }

}
