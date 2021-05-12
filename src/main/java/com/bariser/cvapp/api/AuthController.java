package com.bariser.cvapp.api;

import com.bariser.cvapp.dto.response.AuthenticationResponse;
import com.bariser.cvapp.dto.request.LoginRequest;
import com.bariser.cvapp.dto.response.MessageResponse;
import com.bariser.cvapp.dto.request.SignupRequest;
import com.bariser.cvapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("")
@CrossOrigin("*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        AuthenticationResponse loginResponse = authService.loginUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (!authService.registerUser(signUpRequest)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already in use!"));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
