package com.menglang.Clothing.shop.dto.pageResponse.status;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class StatusResponse {
    private short status;
    private String message;
}
