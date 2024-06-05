package com.example.springsecurity6master._09_authorization_architecture.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

public class MyPreAuthorizeAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {

        Authentication auth = authentication.get();

        if (auth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }

        return new AuthorizationDecision(auth.isAuthenticated());
    }
}
