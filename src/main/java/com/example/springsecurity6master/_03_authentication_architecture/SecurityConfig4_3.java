package com.example.springsecurity6master._03_authentication_architecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig4_3 {
    /**
     *  둘 이상의 AuthenticationProvider를 Bean으로 정의하는 경우
     *      "오히려 간단하다."
     *  -> authenticationManager.parent.providers의 DaoAuthenticationProvider를 자동으로 대체하지 않으므로.
     *      authenticationManager.providers에 순차적으로 추가된다.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider2());


        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                // 위에서 authenticationManagerBuilder.authenticationProvider()를 통해 AuthenticationProvider를 추가하는 방식과 동일한 결과
                /*
                .authenticationProvider(customAuthenticationProvider())
                .authenticationProvider(customAuthenticationProvider2())
                 */
                ;

        return http.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider2() {
        return new CustomAuthenticationProvider2();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
