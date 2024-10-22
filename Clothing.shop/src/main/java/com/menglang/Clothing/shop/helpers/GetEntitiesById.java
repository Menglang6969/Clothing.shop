package com.menglang.Clothing.shop.helpers;

import com.menglang.Clothing.shop.entity.*;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetEntitiesById {
    @Autowired
    @Lazy
    private final SizeRepository sizeRepository;

    @Autowired
    @Lazy
    private final ColorRepository colorRepository;

    @Autowired
    @Lazy
    private final ProductRepository productRepository;

    @Autowired
    @Lazy
    private final CategoryRepository categoryRepository;

    @Autowired
    @Lazy
    private final BranchRepository branchRepository;

    @Autowired
    @Lazy
    private final CustomerRepository customerRepository;

    @Autowired
    @Lazy
    private final UserRepository userRepository;

    public SizeEntity findSizeById(Long id) throws Exception{
        return sizeRepository.findById(id).orElseThrow(()->new CustomMessageException("Size not found","404"));
    }

    public ColorEntity findColorById(Long id) throws Exception{
        return colorRepository.findById(id).orElseThrow(()->new CustomMessageException("Color not found","404"));
    }

    public CategoryEntity findCategoryById(Long id) throws Exception{
        return categoryRepository.findById(id).orElseThrow(()->new CustomMessageException("Category not found","404"));
    }

    public ProductEntity findProductById(Long id) throws Exception{
        return productRepository.findById(id).orElseThrow(()->new CustomMessageException("Product not found","404"));
    }

    public BranchEntity findBranchById(Long id) throws Exception{
        return branchRepository.findById(id).orElseThrow(()->new CustomMessageException("Branch not found","404"));
    }

    public CustomerEntity findCustomerById(Long id) throws Exception{
        return customerRepository.findById(id).orElseThrow(()->new CustomMessageException("Customer not found","404"));
    }
}
