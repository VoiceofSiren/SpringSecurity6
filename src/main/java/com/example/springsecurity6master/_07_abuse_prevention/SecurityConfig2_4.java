package com.example.springsecurity6master._07_abuse_prevention;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig2_4 {
    /**
    CsrfTokenRequestHandler 인터페이스: 토큰 생성 및 응답을 수행.
                                      HTTP 헤더 또는 요청 파라미터로부터 토큰의 유효성을 검증.
        - 구현 클래스
            1) XorCsrfTokenRequestAttributeHandler (Default)
                - _csrf 및 CsrfToken.class.getName()의 이름으로 HttpServletRequest 속성에 CsrfToken을 저장하며
                  이에 따라 HttpServletRequest로부터 CsrfToken을 꺼내어 참조할 수 있다.
            2) CsrfTokenRequestAttributeHandler
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 1) XorCsrfTokenRequestAttributeHandler
        XorCsrfTokenRequestAttributeHandler xorCsrfTokenRequestAttributeHandler = new XorCsrfTokenRequestAttributeHandler();
        // CSRF 토큰 지연 로딩
        // 지연된 토큰을 사용하지 않고 CsrfToken을 모든 요청마다 로드하는 설정
        xorCsrfTokenRequestAttributeHandler.setCsrfRequestAttributeName(null);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/csrf").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(xorCsrfTokenRequestAttributeHandler))
                ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
