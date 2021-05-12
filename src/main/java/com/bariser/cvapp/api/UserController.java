package com.bariser.cvapp.api;

import com.bariser.cvapp.dto.UserDTO;
import com.bariser.cvapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
        UserDTO user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
}
