package com.example.springsecurity6master._03_authentication_architecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig4_5 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManagerBuilder builder,
                                                   AuthenticationConfiguration configuration) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // [1] AnonymousAuthenticationProvider 이전에 CustomAuthenticationProvider3을 추가한다.
        // 단, 한 개의 AuthenticationProvider를 추가할 경우
        // parent.providers에 있는 DaoAuthenticationProvider가 CustomAuthenticationProvider3으로 자동으로 대체된다.
        // this (== ProviderManager)는 authenticationManagerBuilder에 의해 만들어진 ProviderManager이다.
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());

        // [2] this (== ProviderManager)의 parent.providers에 있는 CustomAuthenticationProvider3을 제거한다.
        // this.parent (== ProviderManager)는 configuration의 자동 설정에 의해 builder를 통해 만들어진 ProviderManager이다.
        ProviderManager authenticationManager = (ProviderManager) configuration.getAuthenticationManager();
        authenticationManager.getProviders().remove(0);

        // [3] Bean으로 주입 받은 AuthenticationManagerBuilder 객체를 통해 DaoAuthenticationProvider를 추가한다.
        builder.authenticationProvider(new DaoAuthenticationProvider());

        http
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                ;

        return http.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider3();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
