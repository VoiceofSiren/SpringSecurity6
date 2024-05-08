package com.example.springsecurity6master._02_authentication_process;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig4 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .rememberMe(rememberMe -> rememberMe
//                        .alwaysRemember(true)
                        .tokenValiditySeconds(3600)
                        .userDetailsService(userDetailsService())
                        .rememberMeParameter("rememberMe")
                        .rememberMeCookieName("rememberMe")
                        .key("security")
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
                .password("{noop}1111")
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password("{noop}1111")
                .roles("USER")
                .build();
        UserDetails user3 = User.withUsername("user3")
                .password("{noop}1111")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
}
