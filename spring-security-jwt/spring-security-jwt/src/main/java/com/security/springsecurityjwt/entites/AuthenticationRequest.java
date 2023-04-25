package com.security.springsecurityjwt.entites;

public class AuthenticationRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username) {
        this.username = username;
    }
}
