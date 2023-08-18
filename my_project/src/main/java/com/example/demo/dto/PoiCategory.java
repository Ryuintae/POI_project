package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoiCategory {
    private int categoryCode;
    private int lclascd;
    private String lclasdc;
    private int mlsfccd;
    private String mlsfcdc;
    private int sclascd;
    private String sclasdc;
    private int dclascd;
    private String dclasdc;
    private int bclascd;
    private String bclasdc;
    private String rm;
}