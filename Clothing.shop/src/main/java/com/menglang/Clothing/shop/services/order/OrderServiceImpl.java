package com.menglang.Clothing.shop.services.order;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.entity.AddressEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.repositories.PurchaseOrderRepository;
import com.menglang.Clothing.shop.services.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    @Autowired
    private final ProductServiceImpl productService;

    @Autowired
    private final PurchaseOrderRepository purchaseOrderRepository;



    @Override
    public OrderEntity findOrderById(Long id) throws Exception {
        return null;
    }

    @Override
    public List<OrderEntity> userOrderHistory(Long user_id) throws Exception {
        return List.of();
    }

    @Override
    public ResponseErrorTemplate createOrder(UserEntity user, AddressEntity shipping) throws Exception {
        return null;
    }

    @Override
    public OrderEntity placeOrder(Long orderId) throws Exception {
        return null;
    }

    @Override
    public OrderEntity confirmOrder(Long id) throws Exception {
        return null;
    }

    @Override
    public OrderEntity shipOrder(Long id) throws Exception {
        return null;
    }

    @Override
    public OrderEntity deliveredOrder(Long id) throws Exception {
        return null;
    }

    @Override
    public OrderEntity cancelOrder(Long id) throws Exception {
        return null;
    }

    @Override
    public List<OrderEntity> getAlOrders() {
        return List.of();
    }

}
