package com.bariser.cvapp.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(max=60)
    @Email
    private String email;

    @NotBlank
    @Size(min=5, max = 45)
    private String password;

}
