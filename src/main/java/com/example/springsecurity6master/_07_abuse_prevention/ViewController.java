package com.example.springsecurity6master._07_abuse_prevention;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/form")
    public String form() {
        return "form";
    }

    @GetMapping("/cookie")
    public String cookie() {
        return "cookie";
    }
}
