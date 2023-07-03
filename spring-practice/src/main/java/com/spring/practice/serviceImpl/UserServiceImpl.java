package com.spring.practice.serviceImpl;

import com.spring.practice.dto.Emaildto;
import com.spring.practice.entites.User;
import com.spring.practice.entites.UserInfo;
import com.spring.practice.helper.ReadingTemplates;
import com.spring.practice.mail.MailServiceImpl;
import com.spring.practice.repository.UserInfoRepository;
import com.spring.practice.repository.UserRepository;
import com.spring.practice.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
    private MailServiceImpl mailService;
    @Autowired
    private ReadingTemplates readingTemplates;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public UserInfo addUser(UserInfo userInfo)
    {
userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }



}
