package com.example.springsecurity6master._09_authorization_architecture.method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomMethodInterceptor implements MethodInterceptor {
    private final AuthorizationManager<MethodInvocation> authorizationManager;

    public CustomMethodInterceptor(AuthorizationManager<MethodInvocation> authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        // [1] 인증 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // [2] 권한 심사
        if (authorizationManager.check(() -> authentication, invocation).isGranted()) {
            return invocation.proceed();
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}