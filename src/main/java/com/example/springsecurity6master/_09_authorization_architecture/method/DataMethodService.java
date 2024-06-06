package com.example.springsecurity6master._09_authorization_architecture.method;

import com.example.springsecurity6master._09_authorization_architecture.Account;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DataMethodService {

    public String getUser() {
        return "user";
    }

    public Account getOwner(String name) {
        return new Account(name, false);
    }
    public String display() {
        return "display";
    }
}