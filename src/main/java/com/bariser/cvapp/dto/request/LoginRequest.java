package com.bariser.cvapp.dto.request;

import com.bariser.cvapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    @Size(max=60)
    @Email
    private String email;

    @NotBlank
    @Size(min=5, max = 45)
    private String password;

    private Role role;

}
