package com.example.demo.controller;

import com.example.demo.dto.PoiCategory;
import com.example.demo.service.PoiCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoiCategoryController {

    private final PoiCategoryService poiCategoryService;

    public PoiCategoryController(PoiCategoryService poiCategoryService) {
        this.poiCategoryService = poiCategoryService;
    }

    @GetMapping("/findDataByLCLASCD")
    public List<PoiCategory> findDataByLCLASCD() {
        return poiCategoryService.findByLCLASCD();
    }

    // 대분류 ID에 해당하는 중분류 값 반환
    @GetMapping("/findDataByLCLASCDAndMLSFCCD")
    public List<PoiCategory> findDataByLCLASCDAndMLSFCCD(@RequestParam int lclascd) {
        return poiCategoryService.findByLCLASCDAndMLSFCCD(lclascd);
    }

    // 대분류 ID에 해당 하면서 중분류 ID에 해당하는 소분류 값 반환
    @GetMapping("/findByMLSFCCDAndSCLASCD")
    public List<PoiCategory> findByMLSFCCDAndSCLASCD(@RequestParam(value = "lclascd", required = false) Integer lclascd,
                                                     @RequestParam(value = "mlsfccd", required = false) Integer mlsfccd) {
        return poiCategoryService.findByMLSFCCDAndSCLASCD(lclascd, mlsfccd);
    }

    // 대분류 ID에 해당 하면서 중분류 ID에 해당하면서 소분류 ID에 해당 하는  상세분류 id값 반환
    @GetMapping("/findBySCLASCDAndDCLASCD")
    public List<PoiCategory> findBySCLASCDAndDCLASCD(@RequestParam(value = "lclascd", required = false) Integer lclascd,
                                                     @RequestParam(value = "mlsfccd", required = false) Integer mlsfccd,
                                                     @RequestParam(value = "sclascd", required = false) Integer sclascd) {
        return poiCategoryService.findBySCLASCDAndDCLASCD(lclascd, mlsfccd, sclascd);
    }

    @GetMapping("/findByDCLASCDAndBCLASCD")
    public List<PoiCategory> findByDCLASCDAndBCLASCD(@RequestParam(value = "lclascd", required = false) Integer lclascd,
                                                     @RequestParam(value = "mlsfccd", required = false) Integer mlsfccd,
                                                     @RequestParam(value = "sclascd", required = false) Integer sclascd,
                                                     @RequestParam(value = "dclascd", required = false) Integer dclascd) {
        return poiCategoryService.findByDCLASCDAndBCLASCD(lclascd, mlsfccd, sclascd, dclascd);
    }
}
