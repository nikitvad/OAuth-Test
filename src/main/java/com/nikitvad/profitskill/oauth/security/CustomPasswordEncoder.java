package com.nikitvad.profitskill.oauth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        System.out.println("ENCODE: " + rawPassword);
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("matches raw: " + rawPassword);
        System.out.println("matches encoded: " + rawPassword);

        return rawPassword.equals(encodedPassword);
    }
}
