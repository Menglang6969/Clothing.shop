package com.menglang.Clothing.shop.dto.purchase.purchaseOrder;
import com.menglang.Clothing.shop.dto.branch.BranchDTO;
import com.menglang.Clothing.shop.dto.customer.CustomerDTO;
import com.menglang.Clothing.shop.dto.purchase.purchaseItems.ItemResponse;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.PurchaseItemEntity;
import com.menglang.Clothing.shop.entity.PurchaseOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    PurchaseOrderMapper INSTANCE= Mappers.getMapper(PurchaseOrderMapper.class);

    @Mapping(source = "customer",target = "customer",qualifiedByName = "mapCustomer")
    @Mapping(target = "purchaseItems",source = "purchaseItems",qualifiedByName = "mapItems")
    @Mapping(target = "discountedPrice",source = "totalDiscountedPrice")
    @Mapping(target = "discountedPercent",source = "totalDiscountedPercent")
    @Mapping(target = "branch",source = "branch",qualifiedByName = "mapBranch")
    PurchaseResponse toPurchaseOrderDTO(PurchaseOrderEntity purchaseOrder);


//    PurchaseOrderEntity toPurchaseOrderEntity(PurchaseOrderRequest request);

    @Mapping(target = "size",source = "size.name")
    @Mapping(target = "color",source = "color.name")
    ItemResponse toPurchaseItemDTO(PurchaseItemEntity itemEntity);

    CustomerDTO toCustomerDTO(CustomerEntity customer);
    BranchDTO toBranchDTO(BranchEntity customer);

    @Named("mapCustomer")
    default CustomerDTO mapCustomer(CustomerEntity customer){
        return this.toCustomerDTO(customer);
    }

    @Named("mapBranch")
    default BranchDTO mapCustomer(BranchEntity branch){
        return this.toBranchDTO(branch);
    }

    @Named("mapItems")
    default ItemResponse mapItems(PurchaseItemEntity items){
        return this.toPurchaseItemDTO(items);
    }

}
