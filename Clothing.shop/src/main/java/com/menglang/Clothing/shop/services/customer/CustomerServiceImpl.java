package com.menglang.Clothing.shop.services.customer;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.customer.CustomerMapper;
import com.menglang.Clothing.shop.dto.customer.CustomerRequest;
import com.menglang.Clothing.shop.dto.customer.CustomerTypeResponse;
import com.menglang.Clothing.shop.dto.pageResponse.BasePageResponse;
import com.menglang.Clothing.shop.entity.CustomerEntity;
import com.menglang.Clothing.shop.entity.enums.CustomerType;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.CustomerRepository;
import com.menglang.Clothing.shop.utils.PageableResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final CustomerMapper customerMapper;

    @Override
    public ResponseTemplate create(CustomerRequest customerRequest) throws Exception {
        try {
            log.info("-------------------customer data: {}",customerRequest.address());
            CustomerEntity customer = CustomerEntity.builder()
                    .phone(customerRequest.phone())
                    .name(customerRequest.name())
                    .address(customerRequest.address())
                    .build();
            CustomerEntity savedCustomer = customerRepository.save(customer);
            return ResponseTemplate.builder()
                    .message("created customer successful")
                    .code("201")
                    .object(customerMapper.toCustomerDTO(savedCustomer))
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public ResponseTemplate update(Long id, CustomerRequest customerRequest) throws Exception {
        CustomerEntity existCustomer = this.findById(id);
        try {
            existCustomer.setAddress(customerRequest.address());
            existCustomer.setName(customerRequest.name());
            existCustomer.setPhone(customerRequest.phone());
            CustomerEntity updateCustomer = customerRepository.save(existCustomer);
            return ResponseTemplate.builder()
                    .object(updateCustomer)
                    .code("200")
                    .message("update successful")
                    .build();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Page<CustomerEntity> getAll(int page, int limit, String sort, String query) throws Exception {
        Pageable pageable= PageableResponse.mapPageable(page,limit,sort);
        return customerRepository.findAllByNameContainingIgnoreCase(query,pageable);
    }



    @Override
    public ResponseTemplate delete(Long id) throws Exception {
        CustomerEntity customer = this.findById(id);
        customerRepository.delete(customer);
        return ResponseTemplate.builder()
                .code("200")
                .message("delete successful")
                .object(customer)
                .build();
    }

    @Override
    public ResponseTemplate getById(Long id) throws Exception {
        CustomerEntity customer = this.findById(id);
        return ResponseTemplate.builder()
                .code("200")
                .message("find successful")
                .object(customer)
                .build();
    }

    private CustomerEntity findById(Long id) throws Exception {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer Not found"));
    }

    public  CustomerTypeResponse checkCustomerType(CustomerType type, Long cid, String generalCustomer) throws Exception {
        CustomerEntity customer = null;
        String general_customer = generalCustomer;
        if (type.equals(CustomerType.SPECIAL)) {
            customer = this.findById(cid);
            general_customer = null;
        }
        return CustomerTypeResponse.builder()
                .generalCustomer(generalCustomer)
                .customer(customer)
                .customerType(type)
                .build();
    }

}
