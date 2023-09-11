package com.example.demo.mapper;

import com.example.demo.dto.Image;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ImageMapper {
    // poi_num 으로 file_name 을 찾는 쿼리
    @Select("SELECT p.poi_num, i.file_name FROM poi_data p JOIN image i ON p.poi_num = i.poi_num WHERE p.poi_num = #{poi_num}")
    Image getImageByPoiNum(@Param("poi_num") int poi_num);

    // route_id 로 file_name 을 찾는 쿼리
    @Select("SELECT r.route_id, i.file_name FROM route_save r JOIN image i ON r.route_id = i.route_id WHERE r.route_id = #{route_id}")
    Image getImageByRouteId(@Param("route_id") int route_id);

    @Insert("INSERT INTO public.image(poi_num,  file_name, file_extention, file_size, file_path) " +
            "VALUES (#{poi_num},  #{file_name}, #{file_extention}, #{file_size}, #{file_path})")
    void insertImage(Image image);

    // Route ID로 이미지 정보 삽입
    @Insert("INSERT INTO public.image(route_id,  file_name, file_extention, file_size, file_path) " +
            "VALUES (#{route_id}, #{file_name}, #{file_extention}, #{file_size}, #{file_path})")
    void insertImageByRouteId(Image image);

    @Select("SELECT i.file_name " +
            "FROM public.image i " +
            "INNER JOIN public.route_save rs ON i.route_id = rs.route_id " +
            "WHERE i.route_id = #{route_id} AND rs.user_id = #{user_id}")
    String getImageFileNameByRouteIdAndUserId(@Param("route_id") int route_id, @Param("user_id") int user_id);

    @Select("SELECT public.poi_data.*, public.image.file_path AS image_path FROM public.poi_data LEFT JOIN public.image ON public.poi_data.user_id = public.image.user_id WHERE public.poi_data.user_id = #{user_id}")
    Image getImageByUserId(@Param("user_id") int user_id);

    @Select("SELECT public.image.file_name FROM public.image INNER JOIN public.poi_data ON public.image.poi_num = public.poi_data.poi_num")
    Image getImageByUserIdAndPoiName(@Param("poi_num") int user_id, @Param("poi_name") String poi_name);

    // poi_num 에 해당하는 이미지를 지우는 쿼리
    @Delete("DELETE FROM public.image WHERE poi_num = #{poi_num}")
    void deleteImageByPoiNum(@Param("poi_num") int poi_num);

    @Delete("DELETE FROM public.image WHERE route_id = #{route_id}")
    void deleteRouteById(@Param("route_id") int route_id);
}
