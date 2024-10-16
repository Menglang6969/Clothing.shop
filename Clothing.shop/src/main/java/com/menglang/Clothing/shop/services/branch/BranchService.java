package com.menglang.Clothing.shop.services.branch;


import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;

import java.util.List;

public interface BranchService {
    public ResponseErrorTemplate create(BranchRequest data) throws Exception;
    public ResponseErrorTemplate update(Long id,BranchRequest data)throws Exception;
    public ResponseErrorTemplate delete(Long id)throws Exception;
    public ResponseErrorTemplate findBranchById(Long id) throws Exception;
}
