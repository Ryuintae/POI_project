package com.example.demo.service;

import com.example.demo.dto.Poi;
import com.example.demo.dto.PoiCategory;

import java.util.List;

public interface PoiCategoryService {
    List<PoiCategory> findByLCLASCD();

    List<PoiCategory> findByLCLASCDAndMLSFCCD(int lclascd);

    List<PoiCategory> findByMLSFCCDAndSCLASCD(Integer lclascd, Integer mlsfccd);

    List<PoiCategory> findBySCLASCDAndDCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd);

    List<PoiCategory> findByDCLASCDAndBCLASCD(Integer lclascd, Integer mlsfccd, Integer sclascd, Integer dclascd);

    List<Poi> getPoisByLclascd(int lclascd);

    List<Poi> getPoisByMlsfccd(int lclascd, int mlsfccd);

    List<Poi> getPoisBySclascd(int lclascd, int mlsfccd, int sclascd);

    List<Poi> getPoisByDclascd(int lclascd, int mlsfccd, int sclascd, int dclascd);

    List<Poi> getPoisByBclascd(int lclascd, int mlsfccd, int sclascd, int dclascd, int bclascd);

}
