package com.example.springsecurity6master._11_integration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
public class SaController {

    private final AsyncService asyncService;

    AuthenticationTrustResolverImpl trustResolver = new AuthenticationTrustResolverImpl();

    @GetMapping("/sa")
    public String index(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        return trustResolver.isAnonymous(authentication)? "anonymous" : "authenticated";
    }

    @GetMapping("/sa-user1")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/sa-user2")
    public String user2(@AuthenticationPrincipal(expression = "username") String user) {
        return user;
    }

    @GetMapping("/sa-current-user")
    public User currentUser(@CurrentUser User user) {
        return user;
    }

    @GetMapping("/sa-current-username")
    public String currentUsername(@CurrentUsername String username) {
        return username;
    }

    @GetMapping("/sa-db")
    public String db() {
        return "db";
    }
    @GetMapping("/sa-admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/sa-login")
    public String login(HttpServletRequest request, MemberDto memberDto) throws ServletException {
        request.login(memberDto.getUsername(), memberDto.getPassword());
        System.out.println("login is successful");
        return "login";
    }

    @GetMapping("/sa-users")
    public List<MemberDto> users(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isAuthenticated = request.authenticate(response);
        if (isAuthenticated) {
            return List.of(new MemberDto("user", "1111"));
        }
        return Collections.emptyList();
    }

    @GetMapping("/sa-callable")
    public Callable<Authentication> call() {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        System.out.println("securityContext = " + securityContext);
        System.out.println("Parent Thread: " + Thread.currentThread().getName());
        return new Callable<Authentication>() {
            @Override
            public Authentication call() throws Exception {
                SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
                System.out.println("securityContext = " + securityContext);
                System.out.println("Child Thread: " + Thread.currentThread().getName());
                return securityContext.getAuthentication();
            }
        };
    }

    @GetMapping("/sa-async")
    public Authentication async() {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        System.out.println("securityContext = " + securityContext);
        System.out.println("Parent Thread: " + Thread.currentThread().getName());

        asyncService.asyncMethod();

        return securityContext.getAuthentication();
    }
}
