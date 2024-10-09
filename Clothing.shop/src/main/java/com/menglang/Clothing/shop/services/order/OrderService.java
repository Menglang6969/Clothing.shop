package com.menglang.Clothing.shop.services.order;


import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.entity.AddressEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.UserEntity;

import java.util.List;

public interface OrderService {
    OrderEntity findOrderById(Long id) throws Exception;
    List<OrderEntity> userOrderHistory(Long user_id) throws Exception;
    ResponseErrorTemplate createOrder(UserEntity user, AddressEntity shipping) throws Exception;
    OrderEntity placeOrder(Long orderId) throws Exception;
    OrderEntity confirmOrder(Long id) throws Exception;
    OrderEntity shipOrder(Long id) throws Exception;
    OrderEntity deliveredOrder(Long id) throws Exception;
    OrderEntity cancelOrder(Long id) throws Exception;
    List<OrderEntity> getAlOrders();


}
