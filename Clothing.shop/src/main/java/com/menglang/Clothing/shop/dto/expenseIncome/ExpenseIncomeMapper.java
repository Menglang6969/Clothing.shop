package com.menglang.Clothing.shop.dto.expenseIncome;

import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseIncomeMapper {
    ExpenseIncomeMapper INSTANCE= Mappers.getMapper(ExpenseIncomeMapper.class);

    @Mapping(target = "expenseIncomeType",source = "type")
    ExpenseIncomeResponse toExpenseIncomeDTO(ExpenseIncomeEntity data);
}
