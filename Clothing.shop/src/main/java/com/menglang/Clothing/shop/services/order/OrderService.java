package com.menglang.Clothing.shop.services.order;


import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.order.OrderRequest;
import com.menglang.Clothing.shop.entity.AddressEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    ResponseTemplate findOrderById(Long id) throws Exception;

    ResponseTemplate makeOrder(OrderRequest request) throws Exception;
}
