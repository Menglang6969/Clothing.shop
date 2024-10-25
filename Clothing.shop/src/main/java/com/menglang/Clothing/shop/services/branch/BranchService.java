package com.menglang.Clothing.shop.services.branch;


import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;

public interface BranchService {
    public ResponseTemplate create(BranchRequest data) throws Exception;
    public ResponseTemplate update(Long id, BranchRequest data)throws Exception;
    public ResponseTemplate delete(Long id)throws Exception;
    public ResponseTemplate findBranchById(Long id) throws Exception;
//    public BranchEntity findById(Long id) throws Exception;
}
