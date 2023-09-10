package com.example.demo.mapper;

import com.example.demo.dto.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ImageMapper {
    @Select("SELECT p.poi_num, i.file_name FROM poi_data p JOIN image i ON p.poi_num = i.poi_num WHERE p.poi_num = #{poi_num}")
    Image getImageByPoiNum(@Param("poi_num") int poi_num);

    @Insert("INSERT INTO public.image(poi_num, save_date, file_name, file_extention, file_size, file_path) " +
            "VALUES (#{poi_num}, #{save_date}, #{file_name}, #{file_extention}, #{file_size}, #{file_path})")
    void insertImage(Image image);

    // Route ID로 이미지 정보 삽입
    @Insert("INSERT INTO public.image(route_id, save_date, file_name, file_extention, file_size, file_path) " +
            "VALUES (#{route_id}, #{save_date}, #{file_name}, #{file_extention}, #{file_size}, #{file_path})")
    void insertImageByRouteId(Image image);

    @Select("SELECT public.poi_data.*, public.image.file_path AS image_path FROM public.poi_data LEFT JOIN public.image ON public.poi_data.user_id = public.image.user_id WHERE public.poi_data.user_id = #{user_id}")
    Image getImageByUserId(@Param("user_id") int user_id);

    @Select("SELECT public.image.file_name FROM public.image INNER JOIN public.poi_data ON public.image.poi_num = public.poi_data.poi_num")
    Image getImageByUserIdAndPoiName(@Param("poi_num") int user_id, @Param("poi_name") String poi_name);


}
