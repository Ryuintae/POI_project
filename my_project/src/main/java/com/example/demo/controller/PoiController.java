package com.example.demo.controller;

import com.example.demo.dto.Poi;
import com.example.demo.service.PoiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/poi/search")
    @ResponseBody
    public List<Poi> searchByName(@RequestParam("name") String poiName) {
        return poiService.findByName(poiName);
    }

    @GetMapping("/poi/hover")
    @ResponseBody
    public List<Poi> getPoiByLatitudeAndLongitude(@RequestParam("lon") double lon, @RequestParam("lat") double lat) {
        return poiService.findByLatitudeAndLongitude(lon, lat);
    }

    @GetMapping("/poi-page")
    public String userPage() {
        return "index2";
    }
}
