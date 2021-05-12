package com.bariser.cvapp.dto;

import com.bariser.cvapp.model.Role;
import lombok.Data;


@Data
public class UserDTO {

    private Long id;
    private String email;
    private CvDTO cv;
    private Role role;
}
