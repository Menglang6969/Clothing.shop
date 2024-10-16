package com.menglang.Clothing.shop.services.user;

import com.menglang.Clothing.shop.dto.*;
import com.menglang.Clothing.shop.dto.auth.AuthenticationRequest;
import com.menglang.Clothing.shop.dto.auth.RegisterResponse;
import com.menglang.Clothing.shop.dto.user.UserRequest;
import com.menglang.Clothing.shop.dto.user.UserResponse;
import com.menglang.Clothing.shop.entity.CartEntity;
import com.menglang.Clothing.shop.entity.RoleEntity;
import com.menglang.Clothing.shop.entity.UserEntity;
import com.menglang.Clothing.shop.exceptions.CustomMessageException;
import com.menglang.Clothing.shop.repositories.RoleRepository;
import com.menglang.Clothing.shop.repositories.UserRepository;
import com.menglang.Clothing.shop.secuity.jwt.JwtServiceImp;
import com.menglang.Clothing.shop.secuity.userDetails.UserPrincipal;
import com.menglang.Clothing.shop.services.cart.cart.CartService;
import com.menglang.Clothing.shop.services.cart.cart.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImp implements UserInterface {
    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtServiceImp jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final CartServiceImpl cartService;
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Override
    public ResponseErrorTemplate create(UserRequest data) {
        this.userRequestValidate(data);

        List<RoleEntity> roles=roleRepository.findAllByNameIn(data.roles());
        Set<RoleEntity> rolesEntities = new HashSet<>(roles);
        UserEntity user = UserEntity.builder().id(0L)
                .username(data.username())
                .password(passwordEncoder.encode(data.password()))
                .email(data.email())
                .phone(data.phone())
                .attempt(0)
                .roles(rolesEntities)
                .build();

       user.setCreatedAt(new Date());

        try {
            UserEntity saveUser=userRepository.save(user);
            CartEntity cartUser=cartServiceImpl.CreateCart(saveUser);

        } catch (Exception e) {
            throw CustomMessageException.builder().message(e.getMessage()).code(String.valueOf(HttpStatus.UNAUTHORIZED.value())).build();
        }
        UserPrincipal userDetail=new UserPrincipal(
                user.getUsername(),
                user.getPassword()
                ,rolesEntities.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));

        var jwtToken = jwtService.generateToken(userDetail);
        var refreshToken=jwtService.refreshToken(userDetail);

        RegisterResponse authRes=new RegisterResponse(
                data.username(),
                data.email(),
                data.roles(),
                data.phone(),
                jwtToken,
                refreshToken
        );


        return new ResponseErrorTemplate("Successful","201",authRes);

    }

    @Override
    public ResponseErrorTemplate authenticate(AuthenticationRequest data){

        log.info(" authentication signIn Res: {}", data.username());
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.username(),
                            data.password()
                    )
            );
            log.info(" authentication signIn Res: {}",authentication.isAuthenticated());
            var user = userRepository.findByUsername(data.username())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid credential."));

            UserPrincipal userDetail=new UserPrincipal(
                    user.getUsername(),
                    user.getPassword()
                    ,user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));

            var jwtToken = jwtService.generateToken(userDetail);
            var refreshToken=jwtService.refreshToken(userDetail);

            RegisterResponse authRes=new RegisterResponse(
                    data.username(),
                    user.getEmail(),
                    user.getRoles().stream().map(RoleEntity::getName).toList(),
                    user.getPhone(),
                    jwtToken,
                    refreshToken
            );

            return new ResponseErrorTemplate("Success","200",authRes);
        }catch (Exception e){
            return new ResponseErrorTemplate(e.getLocalizedMessage(),"400",e.getMessage());
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
        Optional<UserEntity> user=userRepository.findByUsername(username);
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
                user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()),
                user.getCreatedAt()
        );

        return new ResponseErrorTemplate("Successful","200",userResponse);
    }

    private void userRequestValidate(UserRequest userRequest) {
        if (ObjectUtils.isEmpty(userRequest.username())) {
            throw new CustomMessageException("Password can't be blank or null", String.valueOf(HttpStatus.BAD_REQUEST));
        }

        Optional<UserEntity> user = userRepository.findByUsername(userRequest.username());
        if (user.isPresent()) {
            throw new CustomMessageException("User already existed", String.valueOf(HttpStatus.BAD_REQUEST));
        }

        List<String> roles=roleRepository.findAll().stream().map(RoleEntity::getName).toList();
        for (var role:roles){
            if (!roles.contains(role)){
                throw new CustomMessageException("Role is invalid request",
                        String.valueOf(HttpStatus.BAD_REQUEST)
                        );
            }
        }
    }

}
