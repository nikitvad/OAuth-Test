package com.nikitvad.profitskill.oauth.service;

import com.nikitvad.profitskill.oauth.model.AppUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserDetailsService extends UserDetailsService {

    void createUser(Object userDto);

    List<AppUserDetails> getAllUsers();

    long getTotalUsersCount();
}
