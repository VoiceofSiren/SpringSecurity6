package com.example.springsecurity6master._07_abuse_prevention;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig2_2 {
    /**
    CsrfTokenRepository 인터페이스: CsrfToken을 영속화
        - 구현 클래스
            1) HttpSessionCsrfTokenRepository (Default)
                - 요청 헤더인 X-CSRF-TOKEN 또는 요청 파라미터인 _csrf에서 토큰을 읽는다.
            2) CookieCsrfTokenRepository
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1) HttpSessionCsrfTokenRepository
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(httpSessionCsrfTokenRepository))
                ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
