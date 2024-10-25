package com.menglang.Clothing.shop.services.branch;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchMapper;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    @Autowired
    private final BranchRepository branchRepository;

    @Autowired
    private final BranchMapper branchMapper;

    @Override
    public ResponseTemplate create(BranchRequest data) throws Exception {
      try{
          BranchEntity branch=BranchEntity.builder()
                  .name(data.name())
                  .address(data.address())
                  .description(data.description())
                  .build();
          BranchEntity resBranch=branchRepository.save(branch);
          return ResponseTemplate.builder()
                  .object(branchMapper.toBranchDTO(branch))
                  .code("201")
                  .message("created successful")
                  .build();
      }catch (Exception e){
          throw new CustomMessageException(e.getMessage(),"400");
      }
    }

    @Override
    public ResponseTemplate update(Long id, BranchRequest data) throws Exception {
        try {
            BranchEntity branch=findById(id);
            branch.setAddress(data.address());
            branch.setName(data.name());
            branch.setDescription(data.description());
            BranchEntity updatedBranch= branchRepository.save(branch);
            return ResponseTemplate.builder()
                    .object(branchMapper.toBranchDTO(branch))
                    .code("200" )
                    .message("updated successful")
                    .build();
        }catch (Exception e){
            throw new CustomMessageException(e.getMessage(),"400");
        }
    }

    @Override
    public ResponseTemplate delete(Long id) throws Exception {
       try {
           BranchEntity branch=findById(id);
           branchRepository.deleteById(id);
           return ResponseTemplate.builder()
                   .object(branchMapper.toBranchDTO(branch))
                   .message("Branch deleted successful")
                   .code("200")
                   .build();
       }catch (Exception e){
           throw new CustomMessageException(e.getMessage(),"400");
       }
    }

    @Override
    public ResponseTemplate findBranchById(Long id) throws Exception {
        BranchEntity branch=findById(id);
        return ResponseTemplate.builder()
                .code("200")
                .message("Branch found")
                .object(branchMapper.toBranchDTO(branch))
                .build();
    }


    public  BranchEntity findById(Long id)throws Exception{
        return branchRepository.findById(id).orElseThrow(()->new CustomMessageException("Branch Not Founded","404"));
    }
}
