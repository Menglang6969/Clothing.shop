package com.menglang.Clothing.shop.controllers;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerRequest;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BaseResponse;
import com.menglang.Clothing.shop.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/customer")
@RequiredArgsConstructor
public class CustomerController {
    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private final CustomerService customerService;

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
    public ResponseEntity<BaseResponse> getAll() throws Exception {
        Page<BasePageResponse> res=customerService.getAll();
        return BaseResponse.successful( res, "success");
    }
}
