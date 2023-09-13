package com.example.demo.service;

import com.example.demo.dto.Route;

import java.util.List;

public interface RouteService {
    void registerRoute(Route route);

    void updateRoute(Route route);

    void deleteRoute(int route_id, int user_id);

    List<Route> selectRouteById(int route_id, int user_id);

    List<Route> getRoutesWithUserNameByUserId(int user_id);


    /// 어드민
    List<Route> findAllRoutesForAdmin();


}
