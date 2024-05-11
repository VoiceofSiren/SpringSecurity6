package com.example.springsecurity6master._03_authentication_architecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig4_2 {
    /**
     *  한 개의 AuthenticationProvider를 Bean으로 정의하는 경우
     *      -> authenticationManager.parent.providers의 DaoAuthenticationProvider를 자동으로 대체한다.
     *      --> 따라서, DaoAuthenticationProvider를 그대로 사용하고 싶은 경우,
     *          AuthenticationManagerBuilder 또는 AuthenticationConfiguration가 참조할 수 있는 providerManager 객체 (== authenticationManager.parent)를 참조하여
     *          자동으로 대체된 CustomAuthenticationProvider를 제거한 후
     *          수동으로 DaoAuthenticationProvider를 추가해야 한다.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder builder, AuthenticationConfiguration configuration) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
        // [0] authenticationManager.parent (== ProviderManager 객체)를 참조한다.
        ProviderManager providerManager = (ProviderManager) configuration.getAuthenticationManager();
        // [1] authenticationManager.parent.providers의 0번째 provider인 CustomAuthenticationProvider를 제거한다.
        providerManager.getProviders().remove(0);
        // [2] 비어 있는 authenticationManager.parent.providers에 DaoAuthenticationProvider를 추가한다.
        builder.authenticationProvider(new DaoAuthenticationProvider());

        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                ;
        return http.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("user").password("{noop}1111").roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
