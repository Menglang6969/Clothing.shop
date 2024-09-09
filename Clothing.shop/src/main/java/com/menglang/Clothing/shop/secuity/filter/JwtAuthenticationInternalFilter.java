package com.menglang.Clothing.shop.secuity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.Clothing.shop.configs.JwtConfig;

import com.menglang.Clothing.shop.exceptions.CustomMessageExceptionUtils;
import com.menglang.Clothing.shop.secuity.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationInternalFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        var accessToken=request.getHeader(jwtConfig.getHeader());
        log.info("Do filter uri {} ",request.getRequestURI());

        if(accessToken!=null && !accessToken.isBlank() && accessToken.startsWith(jwtConfig.getPrefix())){
            accessToken=accessToken.substring(jwtConfig.getPrefix().length());

            try{
                if(jwtService.isValidToken(accessToken)){
                    Claims claims=jwtService.extractClaims(accessToken);
                    var username=claims.getSubject();

                    List<String> authorities=claims.get("authorities",List.class);

                    if(username!=null){
                        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                        );

                        log.info(" authentication Token: ",authenticationToken);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    }
                }

            }catch (Exception e){

                log.error(" error: ",e.getLocalizedMessage());
              //  throw new CustomMessageException(e.getLocalizedMessage(),String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                var messageException = CustomMessageExceptionUtils.unauthorized();
                var msgJson = objectMapper.writeValueAsString(messageException);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(msgJson);
                return;
            }
        }
        log.info(" do filter {}",request.getRequestURI());
        filterChain.doFilter(request,response);

    }
}
