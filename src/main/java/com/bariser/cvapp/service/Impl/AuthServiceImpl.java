package com.bariser.cvapp.service.Impl;

import com.bariser.cvapp.auth.TokenManager;
import com.bariser.cvapp.dto.UserDTO;
import com.bariser.cvapp.dto.response.AuthenticationResponse;
import com.bariser.cvapp.dto.request.LoginRequest;
import com.bariser.cvapp.dto.request.SignupRequest;
import com.bariser.cvapp.model.Role;
import com.bariser.cvapp.model.User;
import com.bariser.cvapp.service.AuthService;
import com.bariser.cvapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenManager tokenManager, UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse loginUser(LoginRequest loginRequest){
        try{
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = tokenManager.generateToken(loginRequest.getEmail());
            UserDTO userDTO = userService.findByEmail(loginRequest.getEmail());
            return new AuthenticationResponse(
                    userDTO.getId(),authenticate.getName(),
                    token,authenticate.getAuthorities(),userDTO.getCv());
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean registerUser(SignupRequest signUpRequest) {

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return false;
        }

        // Create new user's account
        User user = modelMapper.map(signUpRequest, User.class);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        userService.saveUser(user);
        return true;
    }

}
