package ru.isakaev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
        public void configure( WebSecurity web ) {
        web.ignoring().antMatchers( "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/", "/books", "/authors", "/genres", "/error")
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/books", "/authors", "/genres")
                .authenticated()
            .and()
                .authorizeRequests()
                .antMatchers("/books/*", "/authors/*", "/genres/*")
                .authenticated()
            .and()
                .authorizeRequests()
                .antMatchers("/books/delete/**", "/authors/delete/**", "/genres/delete/**")
                .hasAuthority("CAN_DELETE")
            .and()
                .formLogin()
                    .failureUrl("/error")
                    .defaultSuccessUrl("/")
                    .failureUrl("/error")
                    .failureForwardUrl("/error");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
