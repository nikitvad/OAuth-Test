package com.nikitvad.profitskill.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
/*@EnableResourceServer enables a Spring Security filter that authenticates requests using an incoming OAuth2 token.*/
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /*class ResourceServerConfigurerAdapter implements ResourceServerConfigurer providing methods to adjust the access rules and paths that are protected by OAuth2 security.*/
    private static final String RESOURCE_ID = "my_rest_api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/register*").permitAll()
                .and().authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());

//        anonymous().disable()
//                .requestMatchers().antMatchers("/user/**")
//                .and().authorizeRequests()
//                .antMatchers("/user/**").access("permitAll()")
//                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

}