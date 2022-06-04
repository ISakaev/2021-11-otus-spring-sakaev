package ru.isakaev.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User user = new User("user","user", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        User admin = new User("admin","admin", List.of(new SimpleGrantedAuthority("CAN_DELETE")));

        return new InMemoryUserDetailsManager(List.of(
                user, admin
        ));
    }
}
