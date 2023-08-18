package com.example.demo.service;

import com.example.demo.dto.PoiCategory;

import java.util.List;

public interface PoiCategoryService {
    List<PoiCategory> findByLCLASCD();

    List<PoiCategory> findByLCLASCDAndMLSFCCD(int lclascd);

    List<PoiCategory> findByMLSFCCDAndSCLASCD(Integer lclascd, Integer mlsfccd);

    List<PoiCategory> findBySCLASCDAndDCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd);

    List<PoiCategory> findByDCLASCDAndBCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd, Integer dclascd);

}
