package com.menglang.Clothing.shop.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("request_token") String requestToken
) {
}
