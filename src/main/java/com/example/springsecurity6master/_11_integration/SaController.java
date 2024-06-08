package com.example.springsecurity6master._11_integration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SaController {

    @GetMapping("/sa")
    public String index(HttpServletRequest request) {
        return "index";
    }

    @GetMapping("/sa-user")
    public String user() {
        return "user";
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
}
