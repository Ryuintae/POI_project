package com.example.demo.controller;

import com.example.demo.dto.Route;
import com.example.demo.dto.UserVo;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// Jackson 라이브러리 임포트
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerRoute(@ModelAttribute Route route, @AuthenticationPrincipal UserVo loggedInUser) throws Exception {
        if (loggedInUser != null) {
            // 현재 로그인한 사용자의 user_id를 가져옴
            int user_id = loggedInUser.getUser_id();
            // 사용자 정보를 폼에 넣기
            route.setUser_id(user_id);
            //route.setRoute_id(1);
            // 'route' 필드의 좌표 데이터를 WKT 형식의 문자열로 변환
            ObjectMapper mapper = new ObjectMapper();
            List<List<Double>> coordinates = mapper.readValue(route.getRoute(), new TypeReference<List<List<Double>>>() {
            });

            String wkt = coordinates.stream()
                    .map(coord -> coord.get(0) + " " + coord.get(1))
                    .collect(Collectors.joining(", "));

            wkt = "LINESTRING (" + wkt + ")";

            // 변환된 WKT 문자열을 'route' 필드에 설정
            route.setTitle(route.getTitle());
            route.setExplain(route.getExplain());
            route.setRoute(wkt);
            route.setStart(route.getStart());
            route.setEnd_point(route.getEnd_point());
            route.setWaypoints(route.getWaypoints());
            route.setTollFare(route.getTollFare());
            route.setTaxiFare(route.getTaxiFare());
            route.setFuelPrice(route.getFuelPrice());
            route.setSave_time(LocalDateTime.now());
            System.out.println(route.getUser_id());
            System.out.println("시간@@@@@@@@@@@" + route.getSave_time());
            // Service layer를 사용하여 데이터베이스에 데이터를 저장
            routeService.registerRoute(route);

            return ResponseEntity.ok().build();

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

