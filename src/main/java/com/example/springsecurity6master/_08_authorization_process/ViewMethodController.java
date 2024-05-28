package com.example.springsecurity6master._08_authorization_process;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewMethodController {

    @GetMapping("/method")
    public String method() {
        return "method";
    }
}
