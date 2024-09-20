package com.menglang.Clothing.shop.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public record UserResponse(
        String username,
        String password,
        String email,
        @JsonProperty("full_name") String fullName,
        List<String> roles,
        @JsonProperty("created_at") Date createdAt
        ) {
}
