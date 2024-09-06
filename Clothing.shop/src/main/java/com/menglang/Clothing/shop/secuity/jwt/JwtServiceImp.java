package com.menglang.Clothing.shop.secuity.jwt;

import com.menglang.Clothing.shop.configs.JwtConfig;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetail;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImp extends JwtConfig implements JwtService {


    private final CustomUserDetailService userDetailService;

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    @Override
    public SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder().decode(this.getSecrete().getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Override
    public Key getKey() {
        byte[] keys = Decoders.BASE64.decode(this.getSecrete());
        return Keys.hmacShaKeyFor(keys);
    }

    @Override
    public String generateToken(CustomUserDetail customUserDetail) {
        List<String> roles=new ArrayList<>();
        for (GrantedAuthority authority : customUserDetail.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        Instant currentTime=Instant.now();
        return Jwts.builder()
                .subject(customUserDetail.getUsername())
                .claim("authorities",customUserDetail
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim("roles",roles)
                .issuedAt(Date.from(currentTime))
                .expiration(Date.from(currentTime.plusSeconds(Long.parseLong(this.getExpire()))))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String refreshToken(CustomUserDetail customUserDetail) {
        Instant currentTime=Instant.now();

        return Jwts
                .builder()
                .subject(customUserDetail.getUsername())
                .issuedAt(Date.from(currentTime))
                .expiration(Date.from(currentTime.plusSeconds(Long.parseLong(this.getExpire()))))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {

        return false;
    }
}
