package com.example.springsecurity6master._12_advanced_settings;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {

    private boolean flag;

    @Override
    public void init(HttpSecurity http) throws Exception {
        super.init(http);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        MyCustomFilter myCustomFilter = new MyCustomFilter();
        myCustomFilter.setFlag(true);
        http.addFilterBefore(myCustomFilter, UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }

    public boolean setFlag(boolean value) {
        return flag = value;
    }

    public static MyCustomDsl myCustomDsl() {
        return new MyCustomDsl();
    }
}
