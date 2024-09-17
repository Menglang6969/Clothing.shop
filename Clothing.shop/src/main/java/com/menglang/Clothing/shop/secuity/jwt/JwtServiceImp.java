package com.menglang.Clothing.shop.secuity.jwt;

import com.menglang.Clothing.shop.configs.JwtConfig;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.secuity.userDetails.UserPrincipal;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.function.Function;
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
    public String generateToken(UserPrincipal userPrincipal) {
        List<String> roles=new ArrayList<>();
        for (GrantedAuthority authority : userPrincipal.getAuthorities()) {
            roles.add(authority.getAuthority());
        }

        Instant currentTime=Instant.now();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .claim("authorities", userPrincipal
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
    public String refreshToken(UserPrincipal userPrincipal) {
        Instant currentTime=Instant.now();

        return Jwts
                .builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(Date.from(currentTime))
                .expiration(Date.from(currentTime.plusSeconds(Long.parseLong(this.getExpire()))))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        final String username=extractUsername(token);
        UserDetails userDetails= userDetailService.loadUserByUsername(username);
        return userDetails!=null;
    }

    private String extractUsername(String token){
        return extractClaimsTFunction(token, Claims::getSubject);
    }

    private <T> T extractClaimsTFunction(String token, Function<Claims,T> claimsTFunction){
        final Claims claims=extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try{
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        }catch (ExpiredJwtException ex){
            log.error(ex.getLocalizedMessage());
            throw new CustomMessageException("Token was Expired",String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }catch (UnsupportedJwtException e){
            log.error(e.getLocalizedMessage());
            throw new CustomMessageException("Token is not Support",String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }catch (MalformedJwtException e){
            log.error(e.getLocalizedMessage());
            throw new CustomMessageException("Token is Invalid",String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw new CustomMessageException(e.getMessage(),String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }
    }



}
