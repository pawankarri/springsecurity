package com.spring.practice.service;

import com.spring.practice.entites.UserInfo;
import com.spring.practice.entites.UserInfoUserDetails;
import com.spring.practice.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> details=userInfoRepository.findByName(username);
        return details.map(UserInfoUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
