package com.example.demo.service;

import com.example.demo.dto.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User getUserById(Long userId);
}