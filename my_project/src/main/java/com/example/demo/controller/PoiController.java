package com.example.demo.controller;

import com.example.demo.dto.Image;
import com.example.demo.dto.Poi;
import com.example.demo.service.ImageService;
import com.example.demo.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PoiController {


    private final PoiService poiService;
    private final ImageService imageService;
    private final String uploadPath;

    @Autowired
    public PoiController(PoiService poiService, ImageService imageService, @Value("C:\\uploads") String uploadPath) {
        this.poiService = poiService;
        this.imageService = imageService;
        this.uploadPath = uploadPath;
    }

    @PostMapping("/register")
    public String registerPoi(@ModelAttribute Poi poi, @RequestParam(name = "file", required = false) MultipartFile file) {
        poiService.registerPoi(poi);

        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                long fileSize = file.getSize();

                // 파일을 저장할 경로를 Path 객체로 생성
                Path path = Paths.get(uploadPath + "/" + originalFilename);

                // 파일을 저장하는 로직
                Files.write(path, file.getBytes());

                // 파일명에서 확장자를 제거하여 저장
                String fileNameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf("."));

                Image image = new Image();
                //image.setUser_id(userId); // 토큰으로 가져와야 함
                image.setSave_date(LocalDateTime.now());
                image.setFile_name(fileNameWithoutExtension);
                image.setFile_extention(fileExtension);
                image.setFile_size(fileSize);
                image.setFile_path(uploadPath + "/" + originalFilename);

                imageService.insertImageInfo(image);
            } catch (IOException e) {
                e.printStackTrace();
                // 이미지 업로드 실패에 대한 예외 처리 가능
            }
        }

        return "redirect:/";
    }

    @GetMapping("/poi")
    @ResponseBody
    public List<Poi> getPois() {
        return poiService.findAll();
    }

    @GetMapping("/poi/search")
    @ResponseBody
    public List<Poi> searchByName(@RequestParam("name") String poiName) {
        return poiService.findByName(poiName);
    }

    @GetMapping("/poi/hover")
    @ResponseBody
    public Poi getPoiByLatitudeAndLongitude(@RequestParam("id") Long id) {
        return poiService.findById(id);
    }

    @GetMapping("/poi-page")
    public String userPage() {
        return "index2";
    }


}
