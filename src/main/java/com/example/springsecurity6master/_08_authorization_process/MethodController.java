package com.example.springsecurity6master._08_authorization_process;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MethodController {     // SecurityConfig4_1    SecurityConfig4_2

    private final DataService dataService;

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

}
