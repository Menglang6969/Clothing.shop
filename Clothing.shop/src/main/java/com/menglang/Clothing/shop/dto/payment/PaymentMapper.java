package com.menglang.Clothing.shop.dto.payment;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE= Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "orderNo",source = "order.orderNo")
    @Mapping(target = "totalDebt",source = "debt")
    @Mapping(target = "branch",source = "branch",qualifiedByName = "branchMapping")
    PaymentResponse toPaymentDTO(PaymentEntity payment);

    BranchDTO mapToBranchDTO(BranchEntity branch);

    @Named("branchMapping")
    default BranchDTO branchMapping(BranchEntity branch){
        return this.mapToBranchDTO(branch);
    }

}
