package com.menglang.Clothing.shop.secuity.userDetails;

import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
   private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return this.customUserDetail(username);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public UserPrincipal customUserDetail(String username) throws CustomMessageException{
        Optional<UserEntity> user= userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw CustomMessageException.builder()
                    .message("User not found")
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .build();
        }

        return new UserPrincipal(
                user.get().getUsername(),
                user.get().getPassword(),
                user.get().getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );

    }

    public void saveUserAttemptAuthentication(String username){
        System.out.println(" username save attempt: "+username);
        Optional<UserEntity> user= this.userRepository.findByUsername(username);

       if(user.isPresent()){
           int attempt=user.get().getAttempt()+1;
           user.get().setAttempt(attempt);
           user.get().setUpdatedAt(new Date());
           if(user.get().getAttempt()>3){
               log.error("User {} update status blocked",username);
           }
           userRepository.save(user.get());
       }
    }

    public void updateAttempt (String username){
        Optional<UserEntity> user=Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new CustomMessageException("User Not Found", String.valueOf(HttpStatus.UNAUTHORIZED.value()))));

        user.get().setAttempt(0);
        user.get().setUpdatedAt(new Date());
        userRepository.save(user.get());
    }

}
