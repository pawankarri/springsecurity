package com.security.controller;
import com.security.dto.AuthRequest;
import com.security.dto.SignUpDto;
import com.security.entites.User;
import com.security.serviceImpl.JwtServiceImpl;
import com.security.serviceImpl.ServiceImpl;
import com.security.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final ServiceImpl auth;

    private final UserServiceImpl impl;


    private final AuthenticationManager authenticationManager;

    private final JwtServiceImpl service;

    @PostMapping("/sign-up")
    public ResponseEntity<User> addUser(@RequestBody  SignUpDto dto){
        User user= this.auth.signUp(dto);
        return new ResponseEntity<>(user, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest authRequest) throws Exception {
        this.authenticate(authRequest.getUsername(), authRequest.getPassword());
        UserDetails userDetails = this.impl.loadUserByUsername(authRequest.getUsername());
        String token = this.service.generateToken(userDetails.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("token expiration time", this.service.getExpirationDateFromToken(token).toString());
        map.put("token expiration time in milli seconds", this.service.getExpirationDateFromToken(token).getTime());
        map.put("message", "success");
        map.put("status", "success");
        map.put("username", userDetails.getUsername());
        map.put("user-role", userDetails.getAuthorities().stream().map(auth -> auth.getAuthority()));

        return ResponseEntity.ok().body(map);

    }

    private void authenticate(String username,String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }
        catch (DisabledException e)
        {
            throw new DisabledException("user is disabled");
        }
        catch (BadCredentialsException e)
        {
            throw new BadCredentialsException("bad credentials");
        }
    }






}

