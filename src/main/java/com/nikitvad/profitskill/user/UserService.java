package com.nikitvad.profitskill.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto registerUser(UserRegistrationDto userDto);

    List<UserDto> getAllUsers();

    long getTotalUsersCount();

    UserDto getMe();
}
