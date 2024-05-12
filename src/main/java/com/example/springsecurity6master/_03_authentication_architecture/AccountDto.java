package com.example.springsecurity6master._03_authentication_architecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class AccountDto {
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;
}