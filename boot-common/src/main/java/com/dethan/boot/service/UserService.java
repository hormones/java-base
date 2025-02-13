package com.dethan.boot.service;

import com.dethan.boot.entity.User;
import com.dethan.boot.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
