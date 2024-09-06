package com.menglang.Clothing.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("request_token") String requestToken
) {
}
