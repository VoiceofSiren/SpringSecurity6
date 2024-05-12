package com.example.springsecurity6master._03_authentication_architecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity
// @Configuration
public class SecurityConfig5_1 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        /**
         *  하나의 UserDetailsService Bean만을 만들 경우 아래의 방법 1과 방법 2는 생략 가능하다.
         *  단, Bean 객체가 아닌 new 연산자를 통해 생성한 일반 객체의 경우 반드시 방법 1 또는 방법 2를 사용해야만 한다.
         */
        // 방법 1과 방법 2는 동일하게 처리한다.
        /* 방법 1 */
        // authenticationManagerBuilder.userDetailsService(customUserDetailsService());
        /* 방법 2 */
        // http.userDetailsService(customUserDetailsService());

        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                ;

        return http.build();
    }


    /**
     *  authenticationManager.providers.DaoAuthenticationProvider.userDetailsService가 CustomUserDetailsService로 설정된다.
     */
    @Bean
    public UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }
}
