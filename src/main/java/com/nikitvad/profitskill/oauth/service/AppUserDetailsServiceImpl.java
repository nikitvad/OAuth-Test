package com.nikitvad.profitskill.oauth.service;

import com.nikitvad.profitskill.oauth.model.AppUserDetails;
import com.nikitvad.profitskill.oauth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsServiceImpl implements AppUserDetailsService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AppUserDetailsServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void createUser(Object userDto) {
        AppUserDetails appUserDetails = modelMapper.map(userDto, AppUserDetails.class);
        userRepository.save(appUserDetails);
    }

    @Override
    public List<AppUserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public long getTotalUsersCount() {
        return userRepository.count();
    }


}
