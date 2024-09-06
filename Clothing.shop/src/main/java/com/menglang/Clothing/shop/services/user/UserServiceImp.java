package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import com.menglang.Clothing.shop.dto.UserRequest;
import com.menglang.Clothing.shop.dto.UserResponse;
import com.menglang.Clothing.shop.entity.RoleEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.RoleRepository;
import com.menglang.Clothing.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserInterface{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseErrorTemplate create(UserRequest data) {
        try {
                this.userRequestValidate(data);

                //List<RoleEntity> roles=roleRepository.findAllByNameIn(data.roles());
            UserEntity user=UserEntity.builder()
                    .id(0L)
                    .username(data.username())
                    .password(passwordEncoder.encode(data.password()))
                    .email(data.email())
                    .attempt(0)
                    .build();
            userRepository.save(user);
            return this.userMapper(user);
        }catch (Exception e){
            throw CustomMessageException.builder()
                    .message(e.getMessage())
                    .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                    .build();
        }
    }

    @Override
    public ResponseErrorTemplate findById(Long id) {
        return null;
    }

    @Override
    public ResponseErrorTemplate findByUsername(String username) {
        return null;
    }

    private void userRequestValidate(UserRequest userRequest){
        if(ObjectUtils.isEmpty(userRequest.username())){
            throw new CustomMessageException("Password can't be blank or null",
                    String.valueOf(HttpStatus.BAD_REQUEST));
        }

        Optional<UserEntity> user=userRepository.findUserByUsername(userRequest.username());
        if (user.isPresent()){
            throw new CustomMessageException(
                    "User already existed",
                    String.valueOf(HttpStatus.BAD_REQUEST)
            );
        }

//        List<String> roles=roleRepository.findAll().stream().map(RoleEntity::getName).toList();
//        for (var role:roles){
//            if (!roles.contains(role)){
//                throw new CustomMessageException("Role is invalid request",
//                        String.valueOf(HttpStatus.BAD_REQUEST)
//                        );
//            }
//        }
    }
}
