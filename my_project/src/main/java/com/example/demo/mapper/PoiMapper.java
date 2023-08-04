package com.example.demo.mapper;

import com.example.demo.dto.Poi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PoiMapper {

    @Select("select * from public.poi_data;")
    List<Poi> findAll();
}
