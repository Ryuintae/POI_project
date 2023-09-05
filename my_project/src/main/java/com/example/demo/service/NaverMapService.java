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

    public String getDirections(String start, String end) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        String url = apiUrl + "?start=" + start + "&goal=" + end;

        RestTemplate restTemplate = new RestTemplate();
        // 네이버 API 요청에 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders(); // 올바른 HttpHeaders 클래스 사용
        headers.set("X-NCP-APIGW-API-KEY-ID", NAVER_API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", NAVER_API_KEY_SECRET);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // 에러 처리 로직 추가
            return "Error occurred";
        }
    }
}