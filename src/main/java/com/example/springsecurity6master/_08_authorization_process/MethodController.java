package com.example.springsecurity6master._08_authorization_process;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MethodController {     // SecurityConfig4_1    SecurityConfig4_2

    private final DataProcessService dataService;

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

    @PostMapping("/writeList")
    public List<Account> writeList(@RequestBody List<Account> data) {
        return dataService.writeList(data);
    }

    @PostMapping("/writeLMap")
    public Map<String, Account> writeLMap(@RequestBody List<Account> data) {
        Map<String, Account> accountMap = data.stream()
                .collect(Collectors.toMap(account -> account.getOwner(), account -> account));
        return dataService.writeMap(accountMap);
    }

    @GetMapping("/readList")
    public List<Account> readList() {
        return dataService.readList();
    }

    @GetMapping("/readMap")
    public Map<String, Account> readMap() {
        return dataService.readMap();
    }

    @GetMapping("/mm-user")
    @Secured("ROLE_USER")
    public String mmUser() {
        return "mm-user";
    }

    @GetMapping("/mm-isAdmin")
    @IsAdmin
    public String mmIsAdmin() {
        return "mm-isAdmin";
    }

    @GetMapping("/mm-ownerShip")
    @OwnerShip
    public Account mmOwnerShip(String name) {
        return new Account(name, false);
    }

    @GetMapping("/mm-admin")
    @RolesAllowed("ADMIN")
    public String mmAdmin() {
        return "mm-admin";
    }

    @GetMapping("/mm-permitAll")
    @PermitAll
    public String mmPermitAll() {
        return "mm-permitAll";
    }

    @GetMapping("/mm-denyAll")
    @DenyAll
    public String mmDenyAll() {
        return "mm-denyAll";
    }

    @GetMapping("/mm-delete")
    @PreAuthorize("@myAuthorizer.isUser(#root)")
    public String delete() {
        return "mm-delete";
    }

}
