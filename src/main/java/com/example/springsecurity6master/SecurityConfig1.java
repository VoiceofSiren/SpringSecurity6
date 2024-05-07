package com.example.springsecurity6master;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig1 {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(form -> form
//                        .loginPage("/loginPage")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/failed")
                        .usernameParameter("userId")
                        .passwordParameter("passwd")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                System.out.println("authentication: " + authentication);
                                response.sendRedirect("/home");
                            }
                        })
                        // 위의 코드를 아래처럼 lambda 표현식으로 쓸 수 있음.
                        /*
                        .successHandler(((request, response, authentication) -> {
                            System.out.println("authentication: " + authentication);
                            response.sendRedirect("/home");
                        }))
                        */
                        .failureHandler((request, response, exception) -> {
                            System.out.println("exception: " + exception);
                            response.sendRedirect("/login");
                        })
                        .permitAll()
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