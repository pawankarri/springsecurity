package com.spring.practice.Controller;

import com.spring.practice.dto.AuthRequest;
import com.spring.practice.dto.AuthResponse;
import com.spring.practice.entites.User;
import com.spring.practice.entites.UserInfo;
import com.spring.practice.entites.UserInfoUserDetails;
import com.spring.practice.service.UserInfoUserDetailsService;
import com.spring.practice.serviceImpl.JwtServiceImpl;
import com.spring.practice.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    //all users
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtServiceImpl jwtServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers()
    {
        return this.userService.getAllUsers();
    }
    @GetMapping("/get/{username}")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public User getUser(@PathVariable("username") String username)
    {
        return this.userService.getUser(username);
    }
    @PostMapping("/create")
    public User add(@RequestBody User user)
    {
        return this.userService.addUser(user);
    }
    @GetMapping("/welcome")
    public String getWelcome()
    {
        return "welcome to security";
    }
    @PostMapping("/new")
    public UserInfo addNewUser(@RequestBody UserInfo userInfo)
    {
        return userService.addUser(userInfo);
    }
//@PostMapping("/authenticate")
//    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws UsernameNotFoundException {
//    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//    if (authentication.isAuthenticated()) {
//       // UserDetails userDetails = this.userInfoUserDetailsService.loadUserByUsername(authRequest.getUsername());
//        String token=this.jwtServiceImpl.generateToken(authRequest.getUsername());
//        AuthResponse authResponse=new AuthResponse();
//        authResponse.setUsername(this.jwtServiceImpl.getUserNameFromToken(token));
//        authResponse.setToken(token);
//        authResponse.setTokenExpirationTime(this.jwtServiceImpl.getExpirationDateFromToken(token).toString());
//        authResponse.setMessage("success");
//
//        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
//    } else {
//        throw new UsernameNotFoundException("invalid user request!");
//
//    }
//
//}
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String,Object>> login(@RequestBody AuthRequest authRequest) throws Exception {
        this.authenticate(authRequest.getUsername(),authRequest.getPassword());
        UserDetails userDetails=this.userInfoUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token=this.jwtServiceImpl.generateToken(String.valueOf(userDetails));
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("tokenExpTime",this.jwtServiceImpl.getExpirationDateFromToken(token).toString());
        map.put("message","success");
        map.put("userName",userDetails.getUsername());
        map.put("userRole",userDetails.getAuthorities());
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
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
