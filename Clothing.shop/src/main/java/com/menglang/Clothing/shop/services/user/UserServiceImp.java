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
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserInterface {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseErrorTemplate create(UserRequest data) {
        Set<RoleEntity> roles = new HashSet<>();
        try {
            this.userRequestValidate(data);

          //  List<RoleEntity> roles=roleRepository.findAllByNameIn(data.roles());
            log.info("data role",data.roles());
            UserEntity user = UserEntity.builder().id(0L)
                    .username(data.username())
                    .password(passwordEncoder.encode(data.password()))
                    .email(data.email())
                    .attempt(0)

                    .build();
            userRepository.save(user);
            return this.userMapper(user);
        } catch (Exception e) {
            throw CustomMessageException.builder().message(e.getMessage()).code(String.valueOf(HttpStatus.UNAUTHORIZED.value())).build();
        }
    }

    @Override
    public ResponseErrorTemplate findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        var msg="User not found";
        return user.map(this::userMapper)
                .orElse(new ResponseErrorTemplate(msg,"404", new Object()));
    }

    @Override
    public ResponseErrorTemplate findByUsername(String username) {
        Optional<UserEntity> user=userRepository.findUserByUsername(username);
        if(user.isPresent()) {
            return this.userMapper(user.get());
        }

        return new ResponseErrorTemplate("Not found",String.valueOf(HttpStatus.NOT_FOUND.value()),new Object());
    }

    public ResponseErrorTemplate userMapper(UserEntity user) {
        UserResponse userResponse=new UserResponse(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getUsername(),
                user.getRoles().stream().map(role->role.getName()).collect(Collectors.toList()),
                user.getCreatedAt()
        );
        return new ResponseErrorTemplate("Successfull","200",userResponse);
    }

    private void userRequestValidate(UserRequest userRequest) {
        if (ObjectUtils.isEmpty(userRequest.username())) {
            throw new CustomMessageException("Password can't be blank or null", String.valueOf(HttpStatus.BAD_REQUEST));
        }

        Optional<UserEntity> user = userRepository.findUserByUsername(userRequest.username());
        if (user.isPresent()) {
            throw new CustomMessageException("User already existed", String.valueOf(HttpStatus.BAD_REQUEST));
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
