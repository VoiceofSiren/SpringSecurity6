package com.example.springsecurity6master._09_authorization_architecture.method;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewMethodController {


    @GetMapping("/nm-user")
    public String user(){
        return "nm-user";
    }

    @GetMapping("/nm-db")
    public String db(){
        return "nm-db";
    }

    @GetMapping("/nm-admin")
    public String admin(){
        return "nm-admin";
    }
}
