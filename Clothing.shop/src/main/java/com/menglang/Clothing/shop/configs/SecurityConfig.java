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
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableJpaAuditing
public class SecurityConfig extends JwtConfig{

    private final CustomUserDetailService userDetailService;
    private final CustomAuthenticationProvider authenticationProvider;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    @Autowired
    private final JwtConfig jwtConfig;

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserDetailsService myUserService) {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(myUserService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }

    @Autowired
    public void userAuthenticationGlobalConfig(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        AuthenticationManagerBuilder managerBuilder=httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        managerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
//        AuthenticationManager authenticationManager=managerBuilder.build();

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                    auth->auth.requestMatchers("/api/v1/public/accounts/**").permitAll()

                            .requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers("/api/v1/user/**").hasAnyAuthority("USER","ADMIN")
                            .requestMatchers("/api/v1/admin/**").hasAnyAuthority("ADMIN")
                            .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
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
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(
//                                authenticationManager,
//                                jwtService,
//                                objectMapper,
//                                userDetailService
//                        ),
//                        UsernamePasswordAuthenticationFilter.class
//                )
                .addFilterAfter(
                       new JwtAuthenticationInternalFilter(
                              jwtService,
                               objectMapper,
                                jwtConfig,
                               userDetailService
                               ),
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.getOrBuild();
    }

}
