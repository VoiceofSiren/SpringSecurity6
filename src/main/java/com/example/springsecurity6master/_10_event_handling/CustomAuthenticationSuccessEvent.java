package com.example.springsecurity6master._10_event_handling;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

public class CustomAuthenticationSuccessEvent extends AbstractAuthenticationEvent {
    public CustomAuthenticationSuccessEvent(Authentication authentication) {
        super(authentication);
    }
}