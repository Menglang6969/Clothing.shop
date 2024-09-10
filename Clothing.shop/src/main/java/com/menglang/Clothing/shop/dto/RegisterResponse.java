package com.menglang.Clothing.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RegisterResponse(String username,
                               String email,
                               List<String> roles,
                               @JsonProperty("access_token") String accessToken,
                               @JsonProperty("request_token") String request) {
}
