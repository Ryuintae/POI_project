package com.example.demo.controller;

import com.example.demo.dto.Poi;
import com.example.demo.service.PoiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PoiController {

    private final PoiService poiService;

    public PoiController(PoiService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/poi")
    @ResponseBody
    public List<Poi> getPois() {
        return poiService.findAll();
    }

    @GetMapping("/poi-page")
    public String userPage() {
        return "index2";
    }
}
