package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long user_id;
    private String user_name;
    private int user_age;
    private String user_email;
    private String user_password;
    private String user_phone;

    public User() {
    }
}


