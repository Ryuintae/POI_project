package com.example.demo.service;

import com.example.demo.dto.Route;

import java.util.List;

public interface RouteService {
    void registerRoute(Route route);

    void updateRoute(int user_id, Route route);

    void deleteRoute(int route_id, int user_id);

    List<Route> selectRouteById(int route_id, int user_id);
}
