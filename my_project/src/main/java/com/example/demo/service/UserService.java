package com.example.demo.service;

import com.example.demo.dto.UserVo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserVo> findAll();

    UserVo getUserById(Long user_id);

    void joinUser(UserVo userVo);

    PasswordEncoder passwordEncoder();

    @Override
    UserVo loadUserByUsername(String user_email);

    boolean isEmailUnique(String user_email);

}