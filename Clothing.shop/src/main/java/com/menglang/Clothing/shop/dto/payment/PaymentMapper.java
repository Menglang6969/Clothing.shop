package com.menglang.Clothing.shop.dto.payment;

import com.menglang.Clothing.shop.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE= Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "orderNo",source = "order.orderNo")
    PaymentResponse toPaymentDTO(PaymentEntity payment);

}
