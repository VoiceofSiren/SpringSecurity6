package com.example.springsecurity6master._08_authorization_process;

import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;

@Component("myAuthorizer")
public class MyAuthorizer {

    public boolean isUser(MethodSecurityExpressionOperations root) {
        return root.hasAnyAuthority("ROLE_USER");
    }
}
