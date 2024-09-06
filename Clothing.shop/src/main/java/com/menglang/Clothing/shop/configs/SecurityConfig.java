package com.menglang.Clothing.shop.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.Clothing.shop.exceptions.CustomAccessDeniedException;
import com.menglang.Clothing.shop.secuity.Authentication.CustomAuthenticationProvider;
import com.menglang.Clothing.shop.secuity.filter.JwtAuthenticationFilter;
import com.menglang.Clothing.shop.secuity.filter.JwtAuthenticationInternalFilter;
import com.menglang.Clothing.shop.secuity.jwt.JwtService;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends JwtConfig{

    private final CustomUserDetailService userDetailService;
    private final CustomAuthenticationProvider authenticationProvider;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void userAuthenticationGlobalConfig(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        AuthenticationManagerBuilder managerBuilder=httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager=managerBuilder.build();

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                    auth->auth.requestMatchers("/api/v1/public/accounts/**").permitAll()
                            .requestMatchers("/api/console-h2/**").permitAll()
                            .requestMatchers("/api/v1/user/**").hasAnyAuthority("USER","ADMIN")
                            .requestMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN")
                            .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement(
                        session->session.
                                sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(
                        ex->ex.
                                authenticationEntryPoint((((request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED))))
                                .accessDeniedHandler(new CustomAccessDeniedException())
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(
                                authenticationManager,
                                jwtService,
                                objectMapper,
                                userDetailService
                        ),
                        UsernamePasswordAuthenticationFilter.class
                ).addFilterAfter(
                      JwtAuthenticationInternalFilter.
                              builder()
                              .jwtService(jwtService)
                              .jwtConfig(jwtConfig)
                              .objectMapper(objectMapper)
                              .build(),
                JwtAuthenticationInternalFilter.class
                );

        return httpSecurity.build();
    }

}
