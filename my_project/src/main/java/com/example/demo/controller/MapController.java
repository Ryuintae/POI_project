package com.example.demo.controller;

import com.example.demo.service.NaverMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    @Autowired
    private NaverMapService mapService;


    @GetMapping("/directions")

    public String getDirections(@RequestParam("start") String start,
                                @RequestParam("end") String end,
                                @RequestParam(value="waypoints", required=false) String waypoints) {

        return mapService.getDirections(start, end, waypoints);

    }
}