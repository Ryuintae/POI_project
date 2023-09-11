package com.example.demo.service;

import com.example.demo.dto.Image;

public interface ImageService {

    void insertImageInfo(Image image);

    void insertImageByRouteId(Image image);

    Image getImageByUserId(int userId);

    Image getImageByUserIdAndPoiName(int user_id, String poi_name);

    Image getImageByPoiNum(int poi_num);

    String getImageFileNameByRouteIdAndUserId(int route_id, int user_id);

}
