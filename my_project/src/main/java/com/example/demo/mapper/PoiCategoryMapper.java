package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dto.Poi;
import com.example.demo.dto.PoiCategory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PoiCategoryMapper {


    @SelectProvider(type = CategorySQL.class, method = "findByLCLASCD")
    @Results(id = "poiCategoryResultMap", value = {@Result(column = "category_code", property = "categoryCode", id = true)})
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

    // id값을 조회하여 poi_data 의 일치하는 poi 데이터를 찾아주는 쿼리
    @Select("SELECT pd.* FROM public.poi_category_data pcd JOIN public.poi_data pd ON pcd.category_code = pd.category_code WHERE pcd.lclascd = #{lclascd}")
    List<Poi> getPoisByLclascd(int lclascd);

    // lclascd id값과 일치하는  mlsfccd poi_data 를찾아주는 쿼리
    @Select("SELECT pd.* FROM public.poi_category_data pcd JOIN public.poi_data pd ON pcd.category_code = pd.category_code WHERE pcd.lclascd = #{lclascd} AND pcd.mlsfccd = #{mlsfccd}")
    List<Poi> getPoisByMlsfccd(int lclascd, int mlsfccd);

    @Select("SELECT pd.* FROM public.poi_category_data pcd JOIN public.poi_data pd ON pcd.category_code = pd.category_code WHERE pcd.lclascd = #{lclascd} AND pcd.mlsfccd = #{mlsfccd} AND pcd.sclascd = #{sclascd}")
    List<Poi> getPoisBySclascd(int lclascd, int mlsfccd, int sclascd);

    @Select("SELECT pd.* FROM public.poi_category_data pcd JOIN public.poi_data pd ON pcd.category_code = pd.category_code WHERE pcd.lclascd = #{lclascd} AND pcd.mlsfccd = #{mlsfccd} AND pcd.sclascd = #{sclascd} AND pcd.dclascd = #{dclascd}")
    List<Poi> getPoisByDclascd(int lclascd, int mlsfccd, int sclascd, int dclascd);

    @Select("SELECT pd.* FROM public.poi_category_data pcd JOIN public.poi_data pd ON pcd.category_code = pd.category_code WHERE pcd.lclascd = #{lclascd} AND pcd.mlsfccd = #{mlsfccd} AND pcd.sclascd = #{sclascd} AND pcd.dclascd = #{dclascd} AND pcd.bclascd = #{bclascd}")
    List<Poi> getPoisByBclascd(int lclascd, int mlsfccd, int sclascd, int dclascd, int bclascd);

}
