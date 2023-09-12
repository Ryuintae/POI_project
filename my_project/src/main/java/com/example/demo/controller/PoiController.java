package com.example.demo.controller;

import com.example.demo.dto.Image;
import com.example.demo.dto.Poi;
import com.example.demo.dto.UserVo;
import com.example.demo.service.ImageService;
import com.example.demo.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class PoiController {


    private final PoiService poiService;
    private final ImageService imageService;
    private final String uploadPath;

    @Autowired
    public PoiController(PoiService poiService, ImageService imageService, @Value("${upload.path}") String uploadPath) {
        this.poiService = poiService;
        this.imageService = imageService;
        this.uploadPath = uploadPath;
    }

    @PostMapping("/register")
    public String registerPoi(@ModelAttribute Poi poi, @RequestParam(name = "file", required = false) MultipartFile file,
                              @AuthenticationPrincipal UserVo loggedInUser) {
        if (loggedInUser != null) {
            // 현재 로그인한 사용자의 user_id를 가져옴
            int userId = loggedInUser.getUser_id();
            // 사용자 정보를 폼에 넣기
            poi.setUser_id(userId);
            poiService.registerPoi(poi);

            if (file != null && !file.isEmpty()) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String fileExtention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    long fileSize = file.getSize();

                    // 파일을 저장할 경로를 Path 객체로 생성
                    Path path = Paths.get(uploadPath + "/" + originalFilename);

                    // 파일을 저장하는 로직
                    Files.write(path, file.getBytes());

                    // 파일명에서 확장자를 제거하여 저장
                    //String fileNameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf("."));

                    Image image = new Image();
                    image.setPoi_num(poi.getPoi_num());// poi_num 을 주입하는 부분
                    image.setFile_name(originalFilename);
                    image.setFile_extention(fileExtention);
                    image.setFile_size(fileSize);
                    image.setFile_path(uploadPath + "/" + originalFilename);

                    imageService.insertImageInfo(image);
                } catch (IOException e) {
                    e.printStackTrace();
                    // 이미지 업로드 실패에 대한 예외 처리 가능
                }
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
    public Poi getPoiByLatitudeAndLongitude(@RequestParam("id") int id) {
        return poiService.findById(id);
    }

    @GetMapping("/pois")
    @ResponseBody
    public List<Poi> getPoisByUserId(@AuthenticationPrincipal UserVo loggedInUser) {
        if (loggedInUser != null) {
            int userId = loggedInUser.getUser_id();
            return poiService.findByUserId(userId);
        }
        return Collections.emptyList();
    }

    @DeleteMapping("/pois/{poiId}")
    @ResponseBody
    public ResponseEntity<?> deletePois(@AuthenticationPrincipal UserVo loggedInUser, @PathVariable("poiId") int poi_num) {
        if (loggedInUser != null) {
            int user_id = loggedInUser.getUser_id();
            try {
                this.poiService.deleteByUserIdAndPoiNum(user_id, poi_num);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/pois/{poi_num}")
    public @ResponseBody ResponseEntity<Void> updatePoi(
            @AuthenticationPrincipal UserVo loggedInUser,
            @PathVariable("poi_num") int poi_num,
            @RequestParam("poi_name") String poi_name,
            @RequestParam("tel_no") String tel_no,
            @RequestParam("iclas") String iclas,
            @RequestParam("mlsfc") String mlsfc,
            @RequestParam("sclas") String sclas,
            @RequestParam("dclas") String dclas,
            @RequestParam("memo") String memo,
            @RequestParam("address") String address,
            @RequestParam("lon") double lon,
            @RequestParam("lat") double lat,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("category_code") int category_code,
            @RequestParam("zip_code") int zip_code) {

        if (loggedInUser == null)
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int user_id = loggedInUser.getUser_id();

        // Poi 객체 생성 및 필드 설정
        Poi poi = new Poi();
        poi.setPoi_name(poi_name);
        poi.setTel_no(tel_no);

        poi.setIclas(iclas);
        poi.setMlsfc(mlsfc);
        poi.setSclas(sclas);
        poi.setDclas(dclas);

        poi.setCategory_code(category_code);

        poi.setMemo(memo);
        poi.setAddress(address);

        poi.setLon(lon);
        poi.setLat(lat);

        poi.setZip_code(zip_code);
        poi.setUser_id(user_id);
        poi.setPoi_num(poi_num);


        System.out.println(poi_num);

        if (user_id != poi.getUser_id())
            throw new RuntimeException("해당 유저의 POI가 아닙니다.");

        try {
            this.poiService.updateByUserIdAndPoiNum(poi);
            try {
                System.out.println("기존 이미지 없음!!!!!!!!!!");
                System.out.println(poi.getPoi_num());

                if (file != null && !file.isEmpty()) {
                    System.out.println("기존 이미지 있음!!!!!!!!");
                    // 기존 이미지가 있으면 삭제.
                    Image oldImage = imageService.getImageByPoiNum(poi.getPoi_num());
                    if (oldImage != null) {
                        //Path oldImagePath = Paths.get(oldImage.getFile_path());
                        //Files.deleteIfExists(oldImagePath); // 기존 이미지 파일 삭제
                        imageService.deleteImageByPoiNum(poi.getPoi_num());
                    }

                    String originalFilename = file.getOriginalFilename();
                    System.out.println(file.getOriginalFilename());
                    String fileExtention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    long fileSize = file.getSize();

                    // 파일을 저장할 경로를 Path 객체로 생성
                    Path path = Paths.get(uploadPath + "/" + originalFilename);

                    // 파일을 저장하는 로직
                    Files.write(path, file.getBytes());

                    Image image = new Image();
                    image.setPoi_num(poi.getPoi_num());// poi_num 을 주입하는 부분
                    image.setFile_name(originalFilename);
                    image.setFile_extention(fileExtention);
                    image.setFile_size(fileSize);
                    image.setFile_path(uploadPath + "/" + originalFilename);
                    imageService.insertImageInfo(image);

                } else {
                    // 클라이언트가 새로운 파일을 제공하지 않은 경우
                    // 아무런 작업도 수행하지 않으므로, 기존의 이미지는 그대로 유지됩니다.
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    ====================================================================
    //@GetMapping("/list")
    //@PreAuthorize("hasAuthority('ADMIN')")
    //public String getPois(Model model) {
    //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //    UserVo loggedInUser = (UserVo) authentication.getPrincipal();
    //
    //    model.addAttribute("loggedInUser", loggedInUser);
    //
    //    List<Poi> pois = poiService.getAllUserPois();
    //    model.addAttribute("pois", pois);
    //
    //    return "poi-list";
    //}
}
