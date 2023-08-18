package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dto.PoiCategory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PoiCategoryMapper {


    @SelectProvider(type = CategorySQL.class, method = "findByLCLASCD")
    @Results(id = "poiCategoryResultMap", value = {
            @Result(column = "category_code", property = "categoryCode", id = true)
    })
    List<PoiCategory> findByLCLASCD();

    @SelectProvider(type = CategorySQL.class, method = "findByLCLASCDAndMLSFCCD")
    @ResultMap("poiCategoryResultMap")
    List<PoiCategory> findByLCLASCDAndMLSFCCD(@Param("lclascd") Integer lclascd);

    @SelectProvider(type = CategorySQL.class, method = "findByMLSFCCDAndSCLASCD")
    @ResultMap("poiCategoryResultMap")
    List<PoiCategory> findByMLSFCCDAndSCLASCD(@Param("lclascd") int lclascd, @Param("mlsfccd") Integer mlsfccd);

    @SelectProvider(type = CategorySQL.class, method = "findBySCLASCDAndDCLASCD")
    @ResultMap("poiCategoryResultMap")
    List<PoiCategory> findBySCLASCDAndDCLASCD(@Param("lclascd") int lclascd, @Param("mlsfccd") int mlsfccd, @Param("sclascd") Integer sclascd);

    @SelectProvider(type = CategorySQL.class, method = "findByDCLASCDAndBCLASCD")
    @ResultMap("poiCategoryResultMap")
    List<PoiCategory> findByDCLASCDAndBCLASCD(@Param("lclascd") int lclascd, @Param("mlsfccd") int mlsfccd, @Param("sclascd") int sclascd, @Param("dclascd") Integer dclascd);
}
