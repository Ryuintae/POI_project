package com.example.demo.service.impl;

import com.example.demo.dto.Image;
import com.example.demo.mapper.ImageMapper;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageMapper imageMapper;

    @Autowired
    public ImageServiceImpl(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    public Image getImageByPoiNum(int poi_num) {
        return imageMapper.getImageByPoiNum(poi_num);
    }
    @Override
    public void insertImageInfo(Image image) {
        imageMapper.insertImage(image);
    }

    @Override
    public Image getImageByUserId(int user_id) {
        return imageMapper.getImageByUserId(user_id);
    }

    @Override
    public Image getImageByUserIdAndPoiName(int user_id, String poi_name) {
        return imageMapper.getImageByUserIdAndPoiName(user_id, poi_name);
    }


}
