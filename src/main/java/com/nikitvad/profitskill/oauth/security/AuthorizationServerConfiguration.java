package com.nikitvad.profitskill.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAqMdMIfROCgE7+j6Ck7u1kaSfG4QkwF+HF0uKSb7yroU3/fBS\n" +
            "d5/tg6981rgKvdXr5SRIqOJ4MpkixDQYmxSPHYkFgwEoEabsIWeGSoAX+hT3qqIB\n" +
            "tjRKWJvBLPQEt0ySFwZn5TwE4/6HOZyEDgBdsuXRhyo/Rok3Mx277cYxafHb3CQH\n" +
            "Hkh+MHo2XrwSVBJnXdX91stctTP2lRrJ8RoJXWd4U6lnPgCW+UsHRt0DgFftatfw\n" +
            "VhzqY0n08qzOn01gWoeRFXW4Jc6YX9/4zpC0h+p5Yu43zWkvZ/HbFUGaGtDALaNw\n" +
            "0JpXEO80Bj0NJAeDQDQnG45H8Nur2CHOfIEwLQIDAQABAoIBAQCLuKFQIp1QLwFm\n" +
            "AsW6Y1TGg0+sSdLa5rpBEKokrszcqTBXKtI6cc0AjRB9+NH7odKtWUJFfWYgDSH4\n" +
            "51/PUk/AFaTYdOak5ljotnk3x58RyrXVLlTYb6V0gUyEWGZnEB6IrSbpYKxzykxL\n" +
            "50RzCt6GsTap/NFxJTrrOBxRlRdnKhd4/dkGHcMDTnYEiszMRzRqglWffU2YbxXl\n" +
            "Z8dZiri8JaszSX1sYF2880F5zyoLs0FcUVoiLuKj24/q5nH1n9CSz5VdIDxyTMwb\n" +
            "2hJinDTZnKltLDjcQISQ7Sl6uszARr/Aw64/Fii3tmTjU4Qua6cjgAxB18N0yyPc\n" +
            "bM9uOxuBAoGBANg+E4FnFcMDy8X1g3oA4afufCj249Tx6PJYWbm7yrscAezaNfsP\n" +
            "mmsG0enzAwfaQDAg4Y6cYrEsBW7afKjgAuXOxW+sWW3j4ZngUCR0AfmRolUDyPnc\n" +
            "qKTyqAJinQjmC/JrcDFer1/F8j+zVhzGg1OARTZz7sRig6+UX3SrBhPxAoGBAMfP\n" +
            "Ob5UMwmI/PMQFXkomc7DHGoRArt6t/Tv/o+wDFuOwpG+3eq0eQ4EE/mo5ULRu69c\n" +
            "nb8WvnnKWAMwvd6hBI7vwMVPh0fIUELPciroVAPpX8laCEBE59VCWSufP5CAsUmm\n" +
            "qlk+DLbFqkEa92/yFdTMjfme2x8zCXmjdSMihiv9AoGBAIr8ozbaFg+pQfEz01S4\n" +
            "elGgpGcEAP4emnYhuVH61NwcrVQaEPYUWlEOKh+vlTQX4IfOqosqEvkDbV6JyfQV\n" +
            "P2eL1jTn1vcdsCRiG8DCW36YT3lLHIMUg8S5PqmBzqMokWc4DP1+eUQai5DL6L5I\n" +
            "DaK+4D2WmFHba/6PZCctwZNRAoGAEUMn+/eOST6ai+1RzZviXeihZdB+afAaQIET\n" +
            "fxp4lthomurghUx+QOW9TA55cCJqkqXvwviWEDOZEv19CeTjXe38u485Ysw9kugV\n" +
            "NvvHdmcNLXonyFWrqdMHnSsu9K8JohFSGk+wDYJaSTGpOvSxSic6BNzilXl1Uhr7\n" +
            "HcP3z4kCgYAMacuNuVeBymfjSIpOnrhPGg4fZT1maGNOSUMe4iPrNWPdxPSS3CZM\n" +
            "O2hW5yjELmHutr9Jd8viGjZ0zxonAlU/TMVIObvA7PLA6gByRTmhr2hXte5HAT6n\n" +
            "+FyCnLuVkUReAblQnYr3yiSdYXn52duCzqyir9y+b/7kvt7lHLP3jg==\n" +
            "-----END RSA PRIVATE KEY-----\n";

    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqMdMIfROCgE7+j6Ck7u1\n" +
            "kaSfG4QkwF+HF0uKSb7yroU3/fBSd5/tg6981rgKvdXr5SRIqOJ4MpkixDQYmxSP\n" +
            "HYkFgwEoEabsIWeGSoAX+hT3qqIBtjRKWJvBLPQEt0ySFwZn5TwE4/6HOZyEDgBd\n" +
            "suXRhyo/Rok3Mx277cYxafHb3CQHHkh+MHo2XrwSVBJnXdX91stctTP2lRrJ8RoJ\n" +
            "XWd4U6lnPgCW+UsHRt0DgFftatfwVhzqY0n08qzOn01gWoeRFXW4Jc6YX9/4zpC0\n" +
            "h+p5Yu43zWkvZ/HbFUGaGtDALaNw0JpXEO80Bj0NJAeDQDQnG45H8Nur2CHOfIEw\n" +
            "LQIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";

    private static String REALM = "MY_OAUTH_REALM";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("my-trusted-client")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .secret(passwordEncoder.encode("secret"))
                .accessTokenValiditySeconds(120).//Access token is only valid for 2 minutes.
                refreshTokenValiditySeconds(600);//Refresh token is only valid for 10 minutes.
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
                .accessTokenConverter(tokenEnhancer())
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.realm(REALM + "/client");
    }

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}