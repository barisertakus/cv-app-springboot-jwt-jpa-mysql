package com.bariser.cvapp.dto.response;

import com.bariser.cvapp.dto.CvDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private Long id;
    private String email;
    private String token;
    private Collection<?> roles;
    private CvDTO cv;
}
