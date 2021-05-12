package com.bariser.cvapp;

import com.bariser.cvapp.model.Role;
import com.bariser.cvapp.model.User;
import com.bariser.cvapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CvApp implements CommandLineRunner {


    private final UserService userService;

    public CvApp(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CvApp.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        if(userService.findAll().isEmpty()) {
            User user1 = new User();
            user1.setEmail("test@admin.com");
            user1.setRole(Role.ADMIN);
            user1.setPassword(new BCryptPasswordEncoder().encode("testadmin"));
            userService.saveOrUpdate(user1);

            User user2 = new User();
            user2.setEmail("test@user.com");
            user2.setRole(Role.USER);
            user2.setPassword(new BCryptPasswordEncoder().encode("testuser"));
            userService.saveOrUpdate(user2);

            User user3 = new User();
            user3.setEmail("test2@user.com");
            user3.setRole(Role.USER);
            user3.setPassword(new BCryptPasswordEncoder().encode("test2user"));
            userService.saveOrUpdate(user3);

        }


    }

}
