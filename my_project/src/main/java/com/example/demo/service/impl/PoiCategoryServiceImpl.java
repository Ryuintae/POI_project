package com.example.demo.service.impl;

import com.example.demo.dto.Poi;
import com.example.demo.dto.PoiCategory;
import com.example.demo.mapper.PoiCategoryMapper;
import com.example.demo.service.PoiCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiCategoryServiceImpl implements PoiCategoryService {
    @Autowired
    private PoiCategoryMapper poiCategoryMapper;

    @Override
    public List<PoiCategory> findByLCLASCD() {
        return poiCategoryMapper.findByLCLASCD();
    }

    @Override
    public List<PoiCategory> findByLCLASCDAndMLSFCCD(int lclascd) {
        return poiCategoryMapper.findByLCLASCDAndMLSFCCD(lclascd);
    }

    @Override
    public List<PoiCategory> findByMLSFCCDAndSCLASCD(Integer lclascd, Integer mlsfccd) {
        return poiCategoryMapper.findByMLSFCCDAndSCLASCD(lclascd, mlsfccd);
    }

    @Override
    public List<PoiCategory> findBySCLASCDAndDCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd) {
        return poiCategoryMapper.findBySCLASCDAndDCLASCD(lclascd, mlsfccd, sclascd);
    }

    @Override
    public List<PoiCategory> findByDCLASCDAndBCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd, Integer dclascd) {
        return poiCategoryMapper.findByDCLASCDAndBCLASCD(lclascd, mlsfccd, sclascd, dclascd);
    }
    //============================
    @Override
    public List<Poi> getPoisByLclascd(int lclascd) {
        return poiCategoryMapper.getPoisByLclascd(lclascd);
    }

    @Override
    public List<Poi> getPoisByMlsfccd(int lclascd, int mlsfccd) {
        return poiCategoryMapper.getPoisByMlsfccd(lclascd, mlsfccd);
    }

    @Override
    public List<Poi> getPoisBySclascd(int lclascd, int mlsfccd, int sclascd) {
        return poiCategoryMapper.getPoisBySclascd(lclascd, mlsfccd, sclascd);
    }

    @Override
    public List<Poi> getPoisByDclascd(int lclascd, int mlsfccd, int sclascd, int dclascd) {
        return poiCategoryMapper.getPoisByDclascd(lclascd, mlsfccd, sclascd, dclascd);
    }

    @Override
    public List<Poi> getPoisByBclascd(int lclascd, int mlsfccd, int sclascd, int dclascd, int bclascd) {
        return poiCategoryMapper.getPoisByBclascd(lclascd, mlsfccd, sclascd, dclascd, bclascd);
    }
}
