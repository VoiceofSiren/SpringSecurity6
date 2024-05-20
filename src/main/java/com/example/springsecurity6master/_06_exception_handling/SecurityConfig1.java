package com.example.springsecurity6master._06_exception_handling;

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

//@EnableWebSecurity
//@Configuration
public class SecurityConfig1 {
    /*
    1. 인증 예외 - AuthenticationException
        - SecurityContext 내부의 Authentication 객체를 null로+* 초기화

    2. 인가 예외 - AccessDeniedException
        - 익명 사용자인 경우 AuthenticationException을 Handling함
        - 익명 사용자가 아닌 경우 AccessDeniedHandler에게 위임
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(Customizer.withDefaults())

                .exceptionHandling(exception -> exception
                        // [1] 인증: AuthenticationEntryPoint.commence(request, response, authenticationException)
/*                        .authenticationEntryPoint((request, response, authException) -> {
                            System.out.println(authException.getMessage());
                            // redirect URL을 설정할 경우 Spring Security에서 자동으로 생성해 준 로그인 페이지 대신
                            // 개발자가 별도의 로그인 페이지를 직접 만들어줘야 함.
                            response.sendRedirect("/login");
                        })*/
                        // [2] 인가: AccessDeniedHandler.handle(request, response, accessDeniedException)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            System.out.println(accessDeniedException.getMessage());
                            response.sendRedirect("/denied");
                        })
                )
        ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
