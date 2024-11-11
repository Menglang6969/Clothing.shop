package com.menglang.Clothing.shop.dto.pageResponse.page;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class PageResponse{
    private Long count;
    private int page;
    private int size;
    private int totalPage;

}
