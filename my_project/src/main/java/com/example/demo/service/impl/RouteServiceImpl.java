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
    public void updateRoute(Route route) {
        routeMapper.updateRoute(route);
    }

    @Override
    public void deleteRoute(int route_id, int user_id) {
        routeMapper.deleteRoute(route_id, user_id);
    }

    @Override
    public List<Route> selectRouteById(int route_id, int user_id) {
        return routeMapper.selectRouteById(route_id, user_id);
    }

    @Override
    public List<Route> getRoutesWithUserNameByUserId(int user_id) {
        List<Route> routes = routeMapper.getRoutesWithUserNameByUserId(user_id);
        //routes.forEach(e -> System.out.println(e.getRoute()));
        return routes;
    }
}