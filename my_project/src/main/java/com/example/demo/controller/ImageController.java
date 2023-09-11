package com.example.demo.controller;

import com.example.demo.dto.Image;
import com.example.demo.dto.UserVo;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final String uploadPath; // 설정에서 주입받은 파일 업로드 경로

    @Autowired
    public ImageController(ImageService imageService, @Value("${upload.path}") String uploadPath) {
        this.imageService = imageService;
        this.uploadPath = uploadPath;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertImage(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));
            long fileSize = file.getSize();

            // 파일을 저장할 경로를 Path 객체로 생성
            Path path = Paths.get(uploadPath + "/" + originalFilename);

            // 파일을 저장하는 로직
            Files.write(path, file.getBytes());

            // 파일명에서 확장자를 제거하여 저장
            //String fileNameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            Image image = new Image();
            //image.setPoi_num(image.getPoi_num());
            image.setSave_date(LocalDateTime.now());
            image.setFile_name(originalFilename);
            image.setFile_extention(fileExtension);
            image.setFile_size(fileSize);
            image.setFile_path(uploadPath + "/" + originalFilename);

            imageService.insertImageInfo(image);

            return ResponseEntity.ok("Image information saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image information.");
        }
    }

    @GetMapping("/getImageByUserId/{user_id}")
    public ResponseEntity<Image> getImageByUserId(@PathVariable("user_id") int user_id) {
        // 이미지 정보 가져오기
        try {
            var imgData = Optional.ofNullable(imageService.getImageByUserId(user_id));
            if (imgData.isPresent()) {
                return new ResponseEntity<>(imgData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getImageByUserIdAndPoiName/{user_id}/{poi_name}")
    public ResponseEntity<Image> getImageByUserIdAndPoiName(@PathVariable("user_id") int user_id, @PathVariable("poi_name") String poi_name) {
        // 이미지 정보 가져오기
        try {
            var imgData = Optional.ofNullable(imageService.getImageByUserIdAndPoiName(user_id, poi_name));
            if (imgData.isPresent()) {
                return new ResponseEntity<>(imgData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pois/{poi_num}")
    public ResponseEntity<?> getImage(@PathVariable int poi_num) {
        Image image = imageService.getImageByPoiNum(poi_num);
        if (image != null) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/images/{route_id}")
    public ResponseEntity<String> getImageFileName(@PathVariable int route_id, @AuthenticationPrincipal UserVo loggedInUser) {
        if (loggedInUser != null) {
            int user_id = loggedInUser.getUser_id();
            String fileName = imageService.getImageFileNameByRouteIdAndUserId(route_id, user_id);

            if (fileName != null) {
                return new ResponseEntity<>(fileName, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

