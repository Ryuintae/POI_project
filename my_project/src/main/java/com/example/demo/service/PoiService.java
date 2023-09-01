package com.example.demo.service;

import com.example.demo.dto.Poi;

import java.util.List;

public interface PoiService {
    List<Poi> findAll();

    List<Poi> findByName(String poiName);

    Poi findById(int id);

    void registerPoi(Poi poi);

    List<Poi> findByUserId(int userId);

}
