package com.menglang.Clothing.shop.dto.pageResponse.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.menglang.Clothing.shop.dto.pageResponse.page.PageResponse;
import com.menglang.Clothing.shop.dto.pageResponse.status.StatusResponse;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class BodyResponse implements Serializable {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private PageResponse page;


}
