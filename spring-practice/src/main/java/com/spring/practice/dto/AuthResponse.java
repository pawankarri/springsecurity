package com.spring.practice.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponse {
    private  String username;
    private String token;

    private String tokenExpirationTime;

    private String message;
    private List<GrantedAuthority> authorities;
}
