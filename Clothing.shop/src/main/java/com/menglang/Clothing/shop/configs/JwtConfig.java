package com.menglang.Clothing.shop.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConfig {

    @Value("${jwt.url}")
    private String url;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    @Value("${jwt.expire}")
    private String expire;

    @Value("${jwt.secrete}")
    private String secrete;
}
