package com.menglang.Clothing.shop.dto.expenseIncome;

import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.ExpenseIncomeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseIncomeMapper {
    ExpenseIncomeMapper INSTANCE= Mappers.getMapper(ExpenseIncomeMapper.class);

    @Mapping(target = "expenseIncomeType",source = "type")
    @Mapping(target = "branch",source = "branch",qualifiedByName = "branchMapping")
    ExpenseIncomeResponse toExpenseIncomeDTO(ExpenseIncomeEntity data);

    BranchDTO mapToBranchDTO(BranchEntity branch);

    @Named("branchMapping")
    default BranchDTO branchMapping(BranchEntity branch){
        return this.mapToBranchDTO(branch);
    }
}
