package com.example.springsecurity6master._09_authorization_architecture.method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accounts {
    private String owner;
    private boolean isSecure;
}