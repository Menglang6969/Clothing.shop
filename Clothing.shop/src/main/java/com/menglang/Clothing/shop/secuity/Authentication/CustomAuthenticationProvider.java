package com.menglang.Clothing.shop.secuity.Authentication;

import com.menglang.Clothing.shop.entity.RoleEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.UserRepository;
import com.menglang.Clothing.shop.secuity.userDetails.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication income: {} ", authentication);
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        UserDetails userDetails;

            userDetails = userDetailService.loadUserByUsername(username);
            if(userDetails==null){
                throw new UsernameNotFoundException("Invalid credentials");
            }

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new UsernameNotFoundException("Invalid credentials");
            }


       return new UsernamePasswordAuthenticationToken(
                username,
                password,
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private List<GrantedAuthority> grantedAuthorities(List<RoleEntity> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        if (!roles.isEmpty()) {
            roles.forEach(role -> {
                permissions.add(role.getName());
            });
        }

        permissions.forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission)));
        return grantedAuthorities;
    }
}
