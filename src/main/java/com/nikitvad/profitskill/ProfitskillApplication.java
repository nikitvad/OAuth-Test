package com.nikitvad.profitskill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ProfitskillApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfitskillApplication.class, args);
    }


//    @Bean
//    public ClientDetailsService clientDetailsService() {
//        return new ClientDetailsService() {
//            @Override
//            public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//                BaseClientDetails details = new BaseClientDetails();
//                details.setClientId(clientId);
//                details.setAuthorizedGrantTypes(Arrays.asList("authorization_code") );
//                details.setScope(Arrays.asList("read, trust"));
//                details.setResourceIds(Arrays.asList("oauth2-resource"));
//                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//                authorities.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
//                details.setAuthorities(authorities);
//                return details;
//            }
//        };
//    }
}
