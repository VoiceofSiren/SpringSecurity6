package com.example.springsecurity6master._09_authorization_architecture.method;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {
    private String owner;
    private boolean isSecure;
}