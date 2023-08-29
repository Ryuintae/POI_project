package com.example.demo.mapper;

import com.example.demo.dto.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {


    @Insert("INSERT INTO public.image(user_id, save_date, file_name, file_extention, file_size, file_path) " +
            "VALUES (#{user_id}, #{save_date}, #{file_name}, #{file_extention}, #{file_size}, #{file_path})")
    void insertImage(Image image);

}