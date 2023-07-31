package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String map() {
        return "main";
    }

    @GetMapping("/check_saved_path")
    public String pathCheck() {
        return "check_saved_path";
    }
}