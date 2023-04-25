package com.security.springsecurityjwt.controller;

import com.security.springsecurityjwt.config.JwtUtil;
import com.security.springsecurityjwt.entites.AuthenticationRequest;
import com.security.springsecurityjwt.entites.AuthenticationResponse;
import com.security.springsecurityjwt.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HelloResource {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
	private AuthenticationRequest authenticationRequest;
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
    {
        try
    {
       authenticationManager.authenticate(new  UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
    }
catch(BadCredentialsException e)
        {
        throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
final String jwt=jwtUtil.generateToken(userDetails);
return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}