package com.spring.practice.repository;

import com.spring.practice.entites.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
     Optional<UserInfo> findByName(String username) ;

    UserInfo findByEmail(String email);
}
