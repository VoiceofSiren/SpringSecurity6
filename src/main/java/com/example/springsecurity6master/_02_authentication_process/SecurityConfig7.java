package com.example.springsecurity6master._02_authentication_process;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig7 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName("customParam=y");

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/logoutSuccess").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                SavedRequest savedRequest = requestCache.getRequest(request, response);
                                String redirectUrl = savedRequest.getRedirectUrl();
                                response.sendRedirect(redirectUrl);
                            }
                        }))
                .requestCache(cache -> cache.requestCache(requestCache));
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
