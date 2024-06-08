package com.example.springsecurity6master._10_event_handling;

import org.springframework.security.core.AuthenticationException;

public class CustomException extends AuthenticationException {
    public CustomException(String explanation) {
        super(explanation);
    }
}