package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.category.CategoryResponse;
import com.menglang.Clothing.shop.dto.customer.CustomerMapper;
import com.menglang.Clothing.shop.dto.customer.CustomerRequest;
import com.menglang.Clothing.shop.dto.customer.CustomerResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.entity.CategoryEntity;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/customer")
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<ResponseTemplate> create(@RequestBody CustomerRequest data) throws Exception {
        log.info("data request: {}", data.address());
        return ResponseEntity.ok(customerService.create(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> update(@PathVariable("id") Long id, @RequestBody CustomerRequest data) throws Exception {

        return ResponseEntity.ok(customerService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> delete(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(customerService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> get(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(customerService.getById(id));
    }


    @GetMapping()
    public ResponseEntity<BaseResponse> getAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) int page,
            @RequestParam(value = "limit",defaultValue = "10",required = false) int limit,
            @RequestParam(value = "sort-by",defaultValue = "desc:createdAt",required = false) String sortBy,
            @RequestParam(value = "sort-field",defaultValue = "createdAt",required = false) String sortField,
            @RequestParam(value = "query",defaultValue = "",required = false) String query
    ) throws Exception {
        Page<CustomerEntity> customerEntities=customerService.getAll(page,limit,sortBy,query);
        List<CustomerResponse> customerResponses=customerEntities.stream().map(this.customerMapper::toCustomerDTO).toList();
        return BaseResponse.success( customerResponses,customerEntities, "success");
    }
}
