package com.example.demo.mapper;

import com.example.demo.dto.Poi;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PoiMapper {
    @Select("SELECT * FROM public.poi_data;")
    List<Poi> findAll();

    @Select("SELECT * FROM public.poi_data where poi_num=#{poi_num};")
    Poi findById(int poi_num);

    // POI 정보 등록
    @Insert("INSERT INTO public.poi_data (user_id, category_code, poi_name, iclas, mlsfc, sclas, dclas, tel_no, memo, lon, lat, address, zip_code) " + "VALUES (#{user_id}, #{category_code}, #{poi_name}, #{iclas}, #{mlsfc}, #{sclas}, #{dclas}, #{tel_no}, #{memo}, #{lon}, #{lat}, #{address}, #{zip_code});")
    @Options(useGeneratedKeys = true, keyProperty = "poi_num")
    void register(Poi poi);

    // POI 이름 검색
    @Select("SELECT * FROM public.poi_data WHERE poi_name like '%${poi_name}%';")
    List<Poi> findByName(String poi_name);


    @SelectProvider(type = CategorySQL.class, method = "findByNameAndCategory")
    List<Poi> findByNameAndCategory(@Param("poi_name") String poi_name,
                                    @Param("lclascd") Integer lclascd,
                                    @Param("mlsfccd") Integer mlsfccd,
                                    @Param("sclascd") Integer sclascd,
                                    @Param("dclascd") Integer dclascd,
                                    @Param("bclascd") Integer bclascd);

    // user_id에 해당하는 모든 POI 정보 조회
    @Select("SELECT * FROM public.poi_data WHERE user_id = #{user_id};")
    List<Poi> findByUserId(int user_id);

    // user_id에 해당하고 poi_num이 일치한 데이터 삭제
    @Delete("DELETE FROM public.poi_data WHERE poi_num=#{poi_num} AND user_id=#{user_id};")
    void deleteByUserIdAndPoiNum(int user_id, int poi_num);

    // user_id에 해당하고 poi_num이 일치한 POI 정보 업데이트
    @Update("UPDATE public.poi_data SET poi_name=#{poi_name},  tel_no=#{tel_no}, iclas=#{iclas}, mlsfc=#{mlsfc}, sclas=#{sclas}, dclas=#{dclas}, memo=#{memo}, address=#{address} ,zip_code=#{zip_code} ,lon=#{lon}, lat=#{lat},   category_code=#{category_code} WHERE poi_num=#{poi_num} AND user_id=#{user_id};")
    void updateByUserIdAndPoiNum(Poi poi);

    // ====================================================================

    // 모든 사용자가 등록한 POI 정보 조회 (관리자용)
    @Select("SELECT * FROM public.poi_data WHERE user_id IS NOT NULL;")
    List<Poi> findAllUserPoisForAdmin();

}
