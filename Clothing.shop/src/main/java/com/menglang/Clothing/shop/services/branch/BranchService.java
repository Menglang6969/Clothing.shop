package com.menglang.Clothing.shop.services.branch;


import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.enums.SortBy;
import org.springframework.data.domain.Page;

public interface BranchService {
    public ResponseTemplate create(BranchRequest data) throws Exception;
    public ResponseTemplate update(Long id, BranchRequest data)throws Exception;
    public ResponseTemplate delete(Long id)throws Exception;
    public ResponseTemplate findBranchById(Long id) throws Exception;
    public Page<BranchEntity> findAllBranches(int page, int limit, SortBy sort,String sortByField,String query)throws Exception;
//    public BranchEntity findById(Long id) throws Exception;
}
