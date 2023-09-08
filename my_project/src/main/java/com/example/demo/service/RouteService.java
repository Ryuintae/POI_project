package com.example.demo.service;

import com.example.demo.dto.Route;

import java.util.List;

public interface RouteService {
    void registerRoute(Route route);

    void updateRoute(int user_id, Route route);

    void deleteRoute(int route_id, int user_id);

    List<Route> selectRouteById(int route_id, int user_id);

    List<Route> getRoutesWithUserNameByUserId(int user_id);

}
//$('#routeModalBody').append($(`<p>작성자: ${route.user_name}</p>`));
//    $('#routeModalBody').append($(`<p>제목: ${route.title}</p>`));
//    $('#routeModalBody').append($(`<p>설명: ${route.explain}</p>`));
//    $('#routeModalBody').append($(`<p>저장 시간: ${route.save_time}</p>`));
//    $('#routeModalBody').append($(`<p>출발지: ${route.start}</p>`));
//    $('#routeModalBody').append($(`<p>도착지: ${route.end_point}</p>`));
//    $('#routeModalBody').append($(`<p>경유지: ${route.waypoints}</p>`));
//    $('#routeModalBody').append($(`<p>톨게이트 비용 : ${route.tollFare}</p>`));
//    $('#routeModalBody').append($(`<p>택시비용  : ${route.taxiFare}</p>`));
//    $('#routeModalBody').append($(`<p>연료 가격 : ${route.fuelPrice}</p>`));