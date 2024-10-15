package com.menglang.Clothing.shop.dto.pageResponse.error;

import com.menglang.Clothing.shop.dto.pageResponse.status.StatusResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private Boolean success;
    private StatusResponse status;
}
