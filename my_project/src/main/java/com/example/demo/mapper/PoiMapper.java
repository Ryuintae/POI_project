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

    // POI 정보 수정
    @Update("UPDATE public.poi_data SET user_id=#{user_id}, category_code=#{category_code}, poi_name=#{poi_name}, iclas=#{iclas}, mlsfc=#{mlsfc}, sclas=#{sclas}, dclas=#{dclas}, tel_no=#{tel_no}, memo=#{memo}, lon=#{lon}, lat=#{lat} " + "WHERE poi_num=#{poi_num};")
    void update(Poi poi);

    // POI 정보 삭제
    @Delete("DELETE FROM public.poi_data WHERE poi_num=#{poi_num};")
    void delete(int poi_num);

    // POI 이름 검색
    @Select("SELECT * FROM public.poi_data WHERE poi_name like '%${poi_name}%';")
    List<Poi> findByName(String poi_name);

    // user_id에 해당하는 모든 POI 정보 조회
    @Select("SELECT * FROM public.poi_data WHERE user_id = #{user_id};")
    List<Poi> findByUserId(int user_id);
}
