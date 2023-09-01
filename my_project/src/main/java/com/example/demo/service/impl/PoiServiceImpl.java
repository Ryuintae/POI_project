package com.example.demo.service.impl;

import com.example.demo.dto.Poi;
import com.example.demo.mapper.PoiMapper;
import com.example.demo.service.PoiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiServiceImpl implements PoiService {
    @Override
    public void registerPoi(Poi poi) {
        poiMapper.register(poi);
    }

    private final PoiMapper poiMapper;

    public PoiServiceImpl(PoiMapper poiMapper) {
        this.poiMapper = poiMapper;
    }

    @Override
    public List<Poi> findAll() {
        return poiMapper.findAll();
    }

    @Override
    public List<Poi> findByName(String poiName) {
        return poiMapper.findByName(poiName);
    }

    public Poi findById(int id) {
        return poiMapper.findById(id);
    }

    @Override
    public List<Poi> findByUserId(int userId) {
        return poiMapper.findByUserId(userId);
    }

}
