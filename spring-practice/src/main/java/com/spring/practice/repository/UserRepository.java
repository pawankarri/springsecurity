package com.spring.practice.repository;

import com.spring.practice.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    User findByPhoneNumber(String recipientPhoneNumber);
}

