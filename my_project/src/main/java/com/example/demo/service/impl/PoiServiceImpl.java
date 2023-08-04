package com.example.demo.service.impl;

import com.example.demo.dto.Poi;
import com.example.demo.mapper.PoiMapper;
import com.example.demo.service.PoiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiServiceImpl implements PoiService {

    private final PoiMapper poiMapper;

    public PoiServiceImpl(PoiMapper poiMapper) {
        this.poiMapper = poiMapper;
    }

    @Override
    public List<Poi> findAll() {
        return poiMapper.findAll();
    }
}
