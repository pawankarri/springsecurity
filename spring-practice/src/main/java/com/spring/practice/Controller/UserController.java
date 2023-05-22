package com.spring.practice.Controller;

import com.spring.practice.dto.AuthRequest;
import com.spring.practice.entites.User;
import com.spring.practice.entites.UserInfo;
import com.spring.practice.serviceImpl.JwtServiceImpl;
import com.spring.practice.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers()
    {
        return this.userService.getAllUsers();
    }
    @GetMapping("/get/{username}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
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
@PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    if (authentication.isAuthenticated()) {
        return jwtServiceImpl.generateToken(authRequest.getUsername());
    } else {
        throw new UsernameNotFoundException("invalid user request!");
    }

}
}
