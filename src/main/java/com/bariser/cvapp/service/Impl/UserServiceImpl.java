package com.bariser.cvapp.service.Impl;

import com.bariser.cvapp.dto.UserDTO;
import com.bariser.cvapp.model.User;
import com.bariser.cvapp.repository.UserRepository;
import com.bariser.cvapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User saveOrUpdate(User user) {
        return userRepository.saveAndFlush(user);
    }

    public UserDTO saveUser(User user){
        return modelMapper.map(userRepository.save(user),UserDTO.class);
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(user ->
                modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOS;
    }

    public UserDTO findById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
            return modelMapper.map(user.get(),UserDTO.class);
        return null;
    }

    public UserDTO findByEmail(String email){
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user,UserDTO.class);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}
