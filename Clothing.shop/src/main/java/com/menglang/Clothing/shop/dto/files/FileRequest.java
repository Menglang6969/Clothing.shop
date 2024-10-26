package com.menglang.Clothing.shop.dto.files;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record FileRequest(
    String name
)  implements Serializable{
}
