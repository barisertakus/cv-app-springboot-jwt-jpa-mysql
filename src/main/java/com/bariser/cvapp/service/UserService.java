package com.bariser.cvapp.service;

import com.bariser.cvapp.dto.UserDTO;
import com.bariser.cvapp.model.User;

import java.util.List;


public interface UserService {

    User saveOrUpdate(User user);

    UserDTO saveUser(User user);

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByEmail(String email);

    Boolean existsByEmail(String email);
}
