package com.menglang.Clothing.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ResponseErrorTemplate(String message,
                                    String code,
                                    @JsonProperty("data") Object object) {
}
