package com.security.springsecurityjwt.controller;

import com.security.springsecurityjwt.entites.AuthenticationRequest;
import com.security.springsecurityjwt.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HelloResource {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @PostMapping("/hello")
    public String hello()
    {
        return "Hello World";
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
        try
    {
       authenticationManager.autheticate(new  UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),autheticationRequest.getPassword()
    };
}
catch(BadCredentialsException e)
        {
        throw new Exception("Incorrect username or password",e);
        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(authentication.getUsername());
final String jwt=jwtTokenUtil.generateToken(userDetails);
return ResponseEntity.ok(new AuthenticationResponse(jwt));
}
