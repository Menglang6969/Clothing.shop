package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.imports.ImportResponse;
import com.menglang.Clothing.shop.dto.order.OrderMapper;
import com.menglang.Clothing.shop.dto.order.OrderRequest;
import com.menglang.Clothing.shop.dto.order.OrderResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.ImportEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.services.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    private OrderMapper orderMapper;


    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> createPurchase(@PathVariable("id") Long id ) throws Exception{
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody OrderRequest order) throws Exception{
        log.info("invoke crate order..........");
        return ResponseEntity.ok(orderService.makeOrder(order));

    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "desc:createdAt",required = false) String sortBy,
            @RequestParam(value = "branch",defaultValue = "",required = false) Long branch,
            @RequestParam(value = "start-date",required = false) Date startDate,
            @RequestParam(value = "end-date",required = false) Date endDate
    ) throws Exception {
        Page<OrderEntity> orderEntities=orderService.findAll(page,limit,sortBy,branch,startDate,endDate);
        List<OrderResponse> orderResponses=orderEntities.stream().map(this.orderMapper::toOrderDto).toList();
        return BaseResponse.success( orderResponses,orderEntities, "success");
    }
}
