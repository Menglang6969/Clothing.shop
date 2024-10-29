package com.menglang.Clothing.shop.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableResponse {


    public static Pageable mapPageable(int page,int limit,String sort){
        Sort.Direction sortDir=sort.split(":")[0].equals("desc")?Sort.Direction.DESC:Sort.Direction.ASC;
        String sortField=sort.split(":")[1];
        Sort sortBy=Sort.by(sortDir,sortField);
        return PageRequest.of(page-1,limit,sortBy);

    }
}
