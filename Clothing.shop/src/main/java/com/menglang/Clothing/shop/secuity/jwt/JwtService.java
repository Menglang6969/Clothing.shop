package com.menglang.Clothing.shop.secuity.jwt;

import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetail;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.security.Key;

public interface JwtService {
    Claims extractClaims (String token);
    Key getKey();
    String generateToken(CustomUserDetail customUserDetail);
    String refreshToken(CustomUserDetail customUserDetail);
    SecretKey getSignInKey();
    boolean isValidToken(String token);
}
