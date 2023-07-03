package com.spring.practice.service;

import com.spring.practice.entites.User;
import com.spring.practice.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getAllUsers();

    public User getUser(String username);

    public User addUser(User user);
}


