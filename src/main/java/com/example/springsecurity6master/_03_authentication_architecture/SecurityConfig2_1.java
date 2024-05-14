package com.example.springsecurity6master._03_authentication_architecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity
//@Configuration
public class SecurityConfig2_1 {
    /**
     *  HttpSecurity를 통해 AuthenticationManager를 사용하는 방법
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // [0] AuthenticationManager 객체 생성을 위한 빌더 클래스 객체를 HttpSecurity.getSharedObject(...)를 호출하여 생성.
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // [1] AuthenticationManager 객체를 생성하려는 경우
        // build()는 최초 1회만 호출해야 함.
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        // [2] 이미 생성된 AuthenticationManager 객체를 불러오려는 경우
        // getObject()를 호출해야 함. build() 호출 불가.
        AuthenticationManager newAuthManger = authenticationManagerBuilder.getObject();    // build()가 호출된 적 있으면 이와 같이 불러와야 함.

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/login").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                // [3] HttpSecurity에 생성한 AuthenticationManager를 저장한다.
                .authenticationManager(authenticationManager)
                .addFilterBefore(customFilter(http, authenticationManager), UsernamePasswordAuthenticationFilter.class);
        ;
        return http.build();
    }

    // @Bean을 선언하면 안 된다.
    // AuthenticationManager은 Bean이 아니기 때문에 주입받지 못 한다.
    public CustomAuthenticationFilter customFilter(HttpSecurity http, AuthenticationManager authenticationManager) {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(http);
        customAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return customAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return  new InMemoryUserDetailsManager(user);
    }
}
