package com.menglang.Clothing.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserRequest(
        String username,
        String password,
        String email,
        @JsonProperty("full_name") String fullName,
        @JsonProperty("roles") List<String> roles
) {
}
