package com.menglang.Clothing.shop.secuity.userDetails;

import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.UserRepository;
import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return null;
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public CustomUserDetail customUserDetail(String username) throws CustomMessageException{
        Optional<UserEntity> user= Optional.ofNullable(userRepository.findUserByEmail(username));
        if(user.isEmpty()){
            log.error("user not found");
            throw CustomMessageException.builder()
                    .message("user not found")
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .build();
        }

        return new CustomUserDetail(
                user.get().getUsername(),
                user.get().getPassword(),
                user.get().getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );

    }

}
