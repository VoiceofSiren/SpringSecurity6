package com.example.springsecurity6master._09_authorization_architecture;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrmdamController {
    private final DataService dataService;

    @GetMapping("/c-user")
    public String user(){
        return dataService.getUser();
    }

    @GetMapping("/c-owner")
    public Account owner(String name){
        return dataService.getOwner(name);
    }
    @GetMapping("/c-display")
    public String display(){
        return dataService.display();
    }
}
