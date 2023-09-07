package com.example.demo.mapper;

import com.example.demo.dto.Route;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RouteMapper {

    // user_id 를 컨트롤러에서 넣어주고 경로 등록
    @Insert("INSERT INTO public.route_save(user_id, save_time, title, explain, route, start, end_point, waypoints, tollFare, taxiFare, fuelPrice) VALUES(#{user_id},#{save_time}::timestamp, #{title}, #{explain}, ST_GeomFromText(#{route}), #{start}, #{end_point}, #{waypoints}, #{tollFare}, #{taxiFare}, #{fuelPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "route_id")
    void registerRoute(Route route);


    // user_id에 해당하고 route_id 가 일치한 데이터 업데이트
    @Update("UPDATE public.route_save SET title = #{title}, explain = #{explain}, route = ST_GeomFromText(#{route}), start=#{start} , end=#{end} , waypoints=#{waypoints} , tollFare=#{tollFare} , taxiFare=#{taxiFare} , fuelPrice=#{fuelPrice} WHERE route_id = #{route_id} AND user_id=#{user_id}")
    void updateRoute(@Param("user_id") int user_id, @Param("route") Route route);

    // user_id에 해당하고 route_id 가 일치한 데이터 삭제
    @Delete("DELETE FROM public.route_save WHERE route_id = #{route_id} AND user_id = #{user_id}")
    void deleteRoute(@Param("route_id") int route_id, @Param("user_id") int user_id);


    // user_id에 해당하고 route_id 가 일치한 데이터 조회
    @Select("SELECT * FROM public.route_save WHERE route_id = #{route_id} AND user_id=#{user_id}")
    List<Route> selectRouteById(@Param("route_id") int route_id, @Param("user_id") int user_id);
}
