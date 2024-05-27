package com.example.springsecurity6master._08_authorization_process;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodController {

    @GetMapping("/adm")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adm() {
        return "adm";
    }

    @GetMapping("/usr")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String usr() {
        return "usr";
    }

    @GetMapping("/isAuthenticated")
    @PreAuthorize("isAuthenticated()")
    public String isAuthenticated() {
        return "isAuthenticated";
    }

    @GetMapping("/usr/{id}")
    @PreAuthorize("#id == authentication.name")
    public String authId(@PathVariable(name = "id") String id) {
        return "authId";
    }

    @GetMapping("/owner")
    @PostAuthorize("returnObject.owner == authentication.name")
    public Account owner(String name) {
        return new Account(name, false);
    }

    @GetMapping("/isSecure")
    @PostAuthorize("hasAuthority('ROLE_ADMIN') and returnObject.isSecure")
    public Account isSecure(String name, String secure) {
        return new Account(name, secure.equals("Y"));
    }
}
