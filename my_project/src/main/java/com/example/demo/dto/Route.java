package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {
    private LocalDateTime save_time;
    private int route_id;
    private int user_id;
    private String user_name;
    private String title;
    private String explain;
    private String route;
    private String waypoints_route;
    private String start;
    private String end_point;
    private String waypoints;
    private String tollFare;
    private String taxiFare;
    private String fuelPrice;
}