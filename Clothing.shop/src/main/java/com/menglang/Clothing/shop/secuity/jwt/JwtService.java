package com.menglang.Clothing.shop.secuity.jwt;

import com.menglang.Clothing.shop.secuity.userDetails.UserPrincipal;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.security.Key;

public interface JwtService {
    Claims extractClaims (String token);
    Key getKey();
    String generateToken(UserPrincipal userPrincipal);
    String refreshToken(UserPrincipal userPrincipal);
    SecretKey getSignInKey();
    boolean isValidToken(String token);
}
