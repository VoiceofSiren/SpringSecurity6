package com.example.springsecurity6master._09_authorization_architecture;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUser() {
        return "user";
    }
    @PostAuthorize("returnObject.owner == authentication.name")
    public Account getOwner(String name) {
        return new Account(name, false);
    }
    public String display() {
        return "display";
    }
}