package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poi {
    private int poi_num;
    private int user_id;
    private int category_code;
    private String poi_name;
    private String Iclas;
    private String mlsfc;
    private String sclas;
    private String dclas;
    private String tel_no;
    private String memo;
    private float lon;
    private float lat;
}
