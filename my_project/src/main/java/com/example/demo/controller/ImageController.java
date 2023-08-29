package com.example.demo.controller;

import com.example.demo.dto.Image;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final String uploadPath; // 설정에서 주입받은 파일 업로드 경로

    @Autowired
    public ImageController(ImageService imageService, @Value("${C:\\uploads}") String uploadPath) {
        this.imageService = imageService;
        this.uploadPath = uploadPath;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertImage(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file
    ) {
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
            image.setUser_id(userId);
            image.setSave_date(LocalDateTime.now());
            image.setFile_name(fileNameWithoutExtension);
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
}

