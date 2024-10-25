package com.menglang.Clothing.shop.services.customer;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerRequest;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {
    public ResponseTemplate create(CustomerRequest customerRequest) throws Exception;
    public ResponseTemplate update(Long id, CustomerRequest customerRequest) throws Exception;
    public Page<BasePageResponse> getAll() throws Exception;
    public ResponseTemplate delete(Long id) throws Exception;
    public ResponseTemplate getById(Long id) throws Exception;
}
