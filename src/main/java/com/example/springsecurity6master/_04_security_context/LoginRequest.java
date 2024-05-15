package com.example.springsecurity6master._04_security_context;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
