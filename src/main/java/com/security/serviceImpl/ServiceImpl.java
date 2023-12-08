package com.security.serviceImpl;

import com.security.dto.SignUpDto;
import com.security.entites.Role;
import com.security.entites.User;
import com.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl  {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;


    public User signUp(SignUpDto dto){

        User user=new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Role.ADMIN);

        return repository.save(user);

    }
}
