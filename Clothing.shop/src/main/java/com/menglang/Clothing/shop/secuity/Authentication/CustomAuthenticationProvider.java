package com.menglang.Clothing.shop.secuity.Authentication;

import com.menglang.Clothing.shop.entity.RoleEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.UserRepository;
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

import java.util.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication income: ", authentication);
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        Optional<UserEntity> user;

        try {
            user = userRepository.findByUsername(username);

        } catch (Exception e) {
            log.error("user not found ", e.getLocalizedMessage());
            throw new CustomMessageException("User not found", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }

        if (user.isEmpty()) {
            throw new CustomMessageException("User not found.", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        }

        final List<GrantedAuthority> authorities = grantedAuthorities(user.get().getRoles().stream().toList());
        final Authentication auths = new UsernamePasswordAuthenticationToken(username, password, authorities);

        log.info("Authentications out come {}", auths);
        return auths;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
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
