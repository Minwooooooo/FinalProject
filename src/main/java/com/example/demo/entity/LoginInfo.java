package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginInfo {
    private String name;
    private String token;

    @Builder
    public LoginInfo(String name, String token) {
        this.name = name;
        this.token = token;
    }
}
