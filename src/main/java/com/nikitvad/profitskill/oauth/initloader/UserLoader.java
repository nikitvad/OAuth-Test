package com.nikitvad.profitskill.oauth.initloader;

import com.nikitvad.profitskill.oauth.model.AppUserDetails;
import com.nikitvad.profitskill.oauth.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserLoader {

    private final AppUserDetailsService appUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoader(AppUserDetailsService appUserDetailsService, PasswordEncoder passwordEncoder) {
        this.appUserDetailsService = appUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadUsers() {
        if (appUserDetailsService.getTotalUsersCount() > 0) return;

        AppUserDetails userDetails = AppUserDetails
                .builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .build();


        appUserDetailsService.createUser(userDetails);
        System.out.println("admin created");

        userDetails = AppUserDetails
                .builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .build();

        appUserDetailsService.createUser(userDetails);
        System.out.println("user created");


        userDetails = AppUserDetails
                .builder()
                .username("root")
                .password(passwordEncoder.encode("password"))
                .build();

        appUserDetailsService.createUser(userDetails);
        System.out.println("root created");

    }

    @PostConstruct
    public void init(){
        loadUsers();
    }
}
