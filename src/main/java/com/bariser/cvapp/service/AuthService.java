package com.bariser.cvapp.service;

import com.bariser.cvapp.dto.request.LoginRequest;
import com.bariser.cvapp.dto.request.SignupRequest;
import com.bariser.cvapp.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse loginUser(LoginRequest loginRequest);
    Boolean registerUser(SignupRequest signUpRequest);

}
