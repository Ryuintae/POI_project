package com.example.demo.service.impl;

import com.example.demo.dto.UserVo;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserVo> findAll() {
        return userMapper.findAll();
    }

    @Override
    public UserVo getUserById(Long user_id) {
        return userMapper.getUserById(user_id);
    }

    @Transactional
    public void createAdminUser() {
        UserVo admin = new UserVo();
        admin.setUser_name("admin");
        admin.setUser_age(30);
        admin.setUser_email("admin@test.com");

        String hashedPassword = passwordEncoder().encode("password");

        admin.setUser_password(hashedPassword);

        admin.setUser_phone("010-1234-5678");

        admin.setUser_auth("ADMIN");

        userMapper.saveUser(admin);
    }

    @Transactional
    public void joinUser(UserVo userVo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userVo.setUser_password(passwordEncoder.encode(userVo.getPassword()));
        userVo.setUser_auth("USER");
        userMapper.saveUser(userVo);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserVo loadUserByUsername(String user_password) throws UsernameNotFoundException {
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
        System.out.println(user_password);
        UserVo userVo = userMapper.getUserAccount(user_password);
        if (userVo == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        System.out.println(userVo.getPassword());
        return userVo;
    }

    @Override
    public boolean isEmailUnique(String user_email) {
        return userMapper.findByEmail(user_email) == null;
    }
}