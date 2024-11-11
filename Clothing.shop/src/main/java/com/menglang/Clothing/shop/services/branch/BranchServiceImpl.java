package com.menglang.Clothing.shop.services.branch;

import com.menglang.Clothing.shop.dto.ResponseTemplate;
import com.menglang.Clothing.shop.dto.branch.BranchMapper;
import com.menglang.Clothing.shop.dto.branch.BranchRequest;
import com.menglang.Clothing.shop.entity.BranchEntity;
import com.menglang.Clothing.shop.entity.enums.SortBy;
import com.menglang.Clothing.shop.exceptions.BadRequestException;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.exceptions.NotFoundException;
import com.menglang.Clothing.shop.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
          throw new BadRequestException(e.getMessage());
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
            throw new BadRequestException(e.getMessage());
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
           throw new BadRequestException(e.getMessage());
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

    @Override
    public Page<BranchEntity> findAllBranches(int page, int limit, SortBy sort,String sortByField, String query) throws Exception {
        Sort sortBy=Sort.by(sort.equals(SortBy.ASC)?Sort.Direction.ASC:Sort.Direction.DESC,sortByField);
        Pageable pageable= PageRequest.of(page-1,limit,sortBy);
        return branchRepository.findAllByNameContainingIgnoreCase(query,pageable);
    }


    public  BranchEntity findById(Long id)throws Exception{
        return branchRepository.findById(id).orElseThrow(()->new NotFoundException("Branch Not Founded"));
    }
}
