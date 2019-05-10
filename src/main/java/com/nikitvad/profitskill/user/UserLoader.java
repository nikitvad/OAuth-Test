package com.nikitvad.profitskill.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserLoader {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoader(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadUsers() {
        if (userService.getTotalUsersCount() > 0) return;

        UserRegistrationDto userDetails = UserRegistrationDto
                .builder()
                .username("admin")
                .password("password")
                .build();


        userService.registerUser(userDetails);
        System.out.println("admin created");

        userDetails = UserRegistrationDto
                .builder()
                .username("user")
                .password("password")
                .build();

        userService.registerUser(userDetails);
        System.out.println("user created");


        userDetails = UserRegistrationDto
                .builder()
                .username("root")
                .password("password")
                .build();

        userService.registerUser(userDetails);
        System.out.println("root created");

    }

    @PostConstruct
    public void init(){
        loadUsers();
    }
}
