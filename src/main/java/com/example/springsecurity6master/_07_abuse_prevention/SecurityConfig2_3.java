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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig2_3 {
    /**
    CsrfTokenRepository 인터페이스: CsrfToken을 영속화
        - 구현 클래스
            1) HttpSessionCsrfTokenRepository (Default)
            2) CookieCsrfTokenRepository
                - JavaScript 기반 애플리케이션을 지원하기 위해 CsrfToken을 쿠키에 유지할 수 있다.
                - 기본적으로 XSRF-TOKEN 이름의 쿠키에 작성하고
                  HTTP 요청 헤더인 X-XSRF-TOKEN 또는 요청 파라미터인 _csrf에서 토큰을 읽는다.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 2) CookieCsrfTokenRepository
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();

        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(cookieCsrfTokenRepository))
                ;

        http
                .csrf(csrf -> csrf
                        // JavaScript에서 쿠키를 읽을 수 있도록 HttpOnly를 명시적으로 false로 설정할 수 있다.
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
