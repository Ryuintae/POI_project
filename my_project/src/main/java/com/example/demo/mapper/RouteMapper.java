package com.example.demo.mapper;

import com.example.demo.dto.Route;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RouteMapper {

    // user_id 를 컨트롤러에서 넣어주고 경로 등록
    @Insert("INSERT INTO public.route_save(user_id, title, explain, route, start, end_point, waypoints, tollFare, taxiFare, fuelPrice) VALUES(#{user_id}, #{title}, #{explain}, ST_GeomFromText(#{route}), #{start}, #{end_point}, #{waypoints}, #{tollFare}, #{taxiFare}, #{fuelPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "route_id,save_time", keyColumn = "route_id,save_time")
    // 키를 2개를 지정해줌
    void registerRoute(Route route);


    // user_id에 해당하고 route_id 가 일치한 데이터 업데이트
    @Update("UPDATE public.route_save SET title = #{title}, explain = #{explain} WHERE route_id = #{route_id} AND user_id=#{user_id}")
    void updateRoute(Route route);


    // user_id에 해당하고 route_id 가 일치한 데이터 삭제
    @Delete("DELETE FROM public.route_save WHERE route_id = #{route_id} AND user_id = #{user_id}")
    void deleteRoute(int route_id, int user_id);


    // user_id에 해당하고 route_id 가 일치한 데이터 조회
    @Select("SELECT * FROM public.route_save WHERE route_id = #{route_id} AND user_id=#{user_id}")
    List<Route> selectRouteById(int route_id, int user_id);

    // user_id에 해당하는 모든 경로와 user_name 을 조회
    @Select("SELECT r.save_time,r.route_id , ST_AsText(r.route) as route,r.title, r.explain,r.user_id,r.start,r.end_point,r.waypoints,r.tollfare,r.taxifare,r.fuelprice, public.user.user_name " + "FROM public.route_save r " + "JOIN public.user ON r.user_id = public.user.user_id " + "WHERE r.user_id = #{user_id}")
    List<Route> getRoutesWithUserNameByUserId(int user_id);

}
