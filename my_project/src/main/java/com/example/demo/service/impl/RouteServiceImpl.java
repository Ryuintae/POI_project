package com.example.demo.service.impl;

import com.example.demo.dto.Route;
import com.example.demo.mapper.RouteMapper;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteMapper routeMapper;

    @Autowired
    public RouteServiceImpl(RouteMapper routeMapper) {
        this.routeMapper = routeMapper;
    }

    @Override
    public void registerRoute(Route route) {
        routeMapper.registerRoute(route);
    }

    @Override
    public void updateRoute(int user_id, Route route) {
        routeMapper.updateRoute(user_id, route);
    }

    @Override
    public void deleteRoute(int route_id, int user_id) {
        routeMapper.deleteRoute(route_id, user_id);
    }

    @Override
    public List<Route> selectRouteById(int route_id, int user_id) {
        return routeMapper.selectRouteById(route_id, user_id);
    }
}