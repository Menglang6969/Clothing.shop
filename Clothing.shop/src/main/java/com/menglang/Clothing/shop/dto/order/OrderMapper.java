package com.menglang.Clothing.shop.dto.order;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.customer.CustomerDTO;
import com.menglang.Clothing.shop.dto.order.orderDetails.OrderDetailsResponse;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.OrderEntity;
import com.menglang.Clothing.shop.entity.OrderItemsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer", target = "customer", qualifiedByName = "mapCustomer")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "mapItems")
    @Mapping(target = "branch", source = "branch", qualifiedByName = "mapBranch")
    OrderResponse toOrderDto(OrderEntity order);

    @Mapping(target = "size", source = "size.name")
    @Mapping(target = "color", source = "color.name")
    OrderDetailsResponse toOrderItemDTO(OrderItemsEntity itemEntity);

    CustomerDTO toCustomerDTO(CustomerEntity customer);

    BranchDTO toBranchDTO(BranchEntity customer);

    @Named("mapCustomer")
    default CustomerDTO mapCustomer(CustomerEntity customer) {
        return this.toCustomerDTO(customer);
    }

    @Named("mapBranch")
    default BranchDTO mapCustomer(BranchEntity branch) {
        return this.toBranchDTO(branch);
    }

    @Named("mapItems")
    default OrderDetailsResponse mapItems(OrderItemsEntity items) {
        return this.toOrderItemDTO(items);
    }

}
