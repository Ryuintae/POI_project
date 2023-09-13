package com.example.demo.service;

import com.example.demo.dto.Poi;

import java.util.List;

public interface PoiService {
    List<Poi> findAll();

    List<Poi> findByName(String poiName);

    Poi findById(int id);

    void registerPoi(Poi poi);

    List<Poi> findByUserId(int userId);

    void deleteByUserIdAndPoiNum(int user_id, int poi_num);

    void updateByUserIdAndPoiNum(Poi poi);


    List<Poi> findByNameAndCategory(String poi_name, Integer lclascd, Integer mlsfccd, Integer sclascd, Integer dclascd, Integer bclascd);


    //    ========================================================
    List<Poi> getAllUserPois();

}
