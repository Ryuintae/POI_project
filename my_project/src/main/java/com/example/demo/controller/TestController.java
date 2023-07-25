package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/fruit")
    public String getFruit(Model model) {
        Map<String, String> fruitmap = new HashMap<String, String>();
        fruitmap.put("fruit1", "apple");
        fruitmap.put("fruit2", "banana");
        fruitmap.put("fruit3", "orange");
        model.addAttribute("fruit", fruitmap);
        return "test";
    }

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "반갑습니다!");
        return "hello";
    }

    @GetMapping("/")
    public String map() {
        return "openTest";
    }
}
