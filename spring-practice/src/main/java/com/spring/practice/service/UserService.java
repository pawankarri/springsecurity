package com.spring.practice.service;

import com.spring.practice.entites.User;
import com.spring.practice.repository.UserRepository;

import java.util.List;

public interface UserService  {
    public List<User> getAllUsers();
    public User getUser(String username);
    public User addUser(User user);
}
