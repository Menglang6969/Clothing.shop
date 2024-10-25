package com.menglang.Clothing.shop.dto.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CustomerTypeResponse {

        private CustomerType customerType;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String generalCustomer;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private CustomerEntity customer;
}
