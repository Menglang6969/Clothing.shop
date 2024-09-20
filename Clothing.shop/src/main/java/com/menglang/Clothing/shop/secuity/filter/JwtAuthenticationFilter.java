package com.menglang.Clothing.shop.secuity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.Clothing.shop.dto.auth.AuthenticationRequest;
import com.menglang.Clothing.shop.dto.auth.AuthenticationResponse;
import com.menglang.Clothing.shop.exceptions.CustomMessageExceptionUtils;
import com.menglang.Clothing.shop.secuity.jwt.JwtService;
import com.menglang.Clothing.shop.secuity.userDetails.UserPrincipal;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final OrRequestMatcher requiresAuthenticationRequestMatcher =
           new OrRequestMatcher(
                            new AntPathRequestMatcher("/api/v1/user/**")
                            , new AntPathRequestMatcher("/api/v1/admin/**")
                    );

    private  final JwtService jwtService;
    private final ObjectMapper  objectMapper;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtService jwtService,
                                   ObjectMapper objectMapper,
                                   CustomUserDetailService customUserDetailService) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.customUserDetailService = customUserDetailService;
    }
    

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException,
            IOException,
            ServletException {

      //  log.info("Start attempt to authentication ");
        AuthenticationRequest authenticationRequest=objectMapper.readValue(request.getInputStream(),AuthenticationRequest.class);
     System.out.println("username "+authenticationRequest.username().toString()+" password: "+authenticationRequest.password().toString());
       // customUserDetailService.saveUserAttemptAuthentication(authenticationRequest.username());

        log.info("End Attempt to authentication");

        return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
                  authenticationRequest.username(),
                  authenticationRequest.password()
          )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        UserPrincipal userPrincipal =(UserPrincipal) authResult.getDetails();
        var accessToken=jwtService.generateToken(userPrincipal);
        var refreshToken=jwtService.refreshToken(userPrincipal);

        AuthenticationResponse authenticationResponse=new AuthenticationResponse(
                accessToken,
                refreshToken
        );

        var jsonUser=objectMapper.writeValueAsString(authenticationResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonUser);

        log.info("successful authentication {}",authenticationResponse);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);

        var messageException = CustomMessageExceptionUtils.unauthorized();
        var msgJson = objectMapper.writeValueAsString(messageException);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(msgJson);
        log.info("Unsuccessful Authentication {}", failed.getLocalizedMessage());
    }
}
