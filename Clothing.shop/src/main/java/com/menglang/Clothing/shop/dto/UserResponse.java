package com.menglang.Clothing.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String password,
        String email,
        @JsonProperty("full_name") String fullName,
        @JsonProperty("crated_at")LocalDateTime createdAt
        ) {
}
