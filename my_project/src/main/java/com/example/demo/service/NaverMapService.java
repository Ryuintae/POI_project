package com.example.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;


@Service
public class NaverMapService {
    @Value("${naver.api.key.id}")
    private String NAVER_API_KEY_ID;
    @Value("${naver.api.key.secret}")
    private String NAVER_API_KEY_SECRET;

    public String getDirections(String start, String end, String waypoints) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        String url = apiUrl + "?start=" + start + "&goal=" + end;

        if (waypoints != null && !waypoints.isEmpty()) {
            url += "&waypoints=" + waypoints;
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", NAVER_API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", NAVER_API_KEY_SECRET);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "Error occurred";
        }
    }
}