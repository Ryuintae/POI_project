package com.example.demo.controller;

import com.example.demo.dto.Poi;
import com.example.demo.service.PoiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoiController {

    private final PoiService poiService;

    public PoiController(PoiService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/poi")
    public List<Poi> getPois() {
        return poiService.findAll();
    }
}
