package com.example.demo.controller;

import com.example.demo.dto.Image;
import com.example.demo.dto.Route;
import com.example.demo.dto.UserVo;
import com.example.demo.service.ImageService;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


// Jackson 라이브러리 임포트
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final ImageService imageService;
    private final String uploadPath;

    @Autowired
    public RouteController(RouteService routeService, ImageService imageService, @Value("${upload.path}") String uploadPath) {
        this.routeService = routeService;
        this.imageService = imageService;
        this.uploadPath = uploadPath;
    }

    @PostMapping("/register")
    public String registerRoute(@ModelAttribute Route route, @RequestParam(name = "file2", required = false) MultipartFile file, @AuthenticationPrincipal UserVo loggedInUser) throws Exception {
        if (loggedInUser != null) {
            // 현재 로그인한 사용자의 user_id를 가져옴
            int user_id = loggedInUser.getUser_id();
            // 사용자 정보를 폼에 넣기
            route.setUser_id(user_id);

            // 'route' 필드의 좌표 데이터를 WKT 형식의 문자열로 변환
            ObjectMapper mapper = new ObjectMapper();
            List<List<Double>> routeCoordinates = mapper.readValue(route.getRoute(), new TypeReference<List<List<Double>>>() {
            });

            String wkt = routeCoordinates.stream().map(coord -> coord.get(0) + " " + coord.get(1)).collect(Collectors.joining(", "));

            wkt = "LINESTRING (" + wkt + ")";

            // 변환된 WKT 문자열을 'route' 필드에 설정
            route.setRoute(wkt);

            // Route 등록 및 route_id 값 생성
            routeService.registerRoute(route);

            // 이미지 처리 부분
            if (file != null && !file.isEmpty()) {
                try {
                    String originalFilename = file.getOriginalFilename();
                    String fileExtention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    long fileSize = file.getSize();

                    Path path = Paths.get(uploadPath + "/" + originalFilename);

                    Files.write(path, file.getBytes());

                    Image image = new Image();

                    System.out.println(route.getRoute_id());

                    image.setRoute_id(route.getRoute_id());   // Route id 값을 주입하는 부분
                    image.setFile_name(originalFilename);
                    image.setFile_extention(fileExtention);
                    image.setFile_size(fileSize);
                    image.setFile_path(uploadPath + "/" + originalFilename);

                    imageService.insertImageByRouteId(image);  // 이미지 정보를 DB에 저장합니다.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "redirect:/";
        }
        return "redirect:/login";
    }


    @GetMapping
    public ResponseEntity<List<Route>> getRoutesByLoggedInUser(@AuthenticationPrincipal UserVo loggedInUser) {
        if (loggedInUser != null) {
            int user_id = loggedInUser.getUser_id();
            List<Route> routes = routeService.getRoutesWithUserNameByUserId(user_id);
            return ResponseEntity.ok(routes);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@AuthenticationPrincipal UserVo loggedInUser, @PathVariable("routeId") int route_id) {
        if (loggedInUser != null) {
            int user_id = loggedInUser.getUser_id();
            try {
                this.routeService.deleteRoute(route_id, user_id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{route_id}")
    public @ResponseBody ResponseEntity<Void> updateRoute
            (@AuthenticationPrincipal UserVo loggedInUser, @PathVariable("route_id") int route_id,
             @RequestParam("title") String title, @RequestParam("explain") String explain,
             @RequestParam(name = "file2", required = false) MultipartFile file) {

        if (loggedInUser == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        int user_id = loggedInUser.getUser_id();

        // Route 객체 생성 및 필드 설정
        Route route = new Route();
        route.setTitle(title);
        route.setExplain(explain);
        System.out.println("title!!!!!!!!!!!!!!!!!" + title);
        System.out.println("explain@@@@@@@@@@" + explain);

        route.setUser_id(user_id);
        route.setRoute_id(route_id);

        System.out.println("user_id!!!!!!!!!!!!!!!!!" + user_id);
        System.out.println("route_id@@@@@@@@@@" + route_id);

        if (user_id != route.getUser_id()) throw new RuntimeException("해당 유저의 경로가 아닙니다.");

        try {
            this.routeService.updateRoute(route);
            try {
                if (file != null && !file.isEmpty()) {

                    // 기존 이미지가 있으면 삭제.
                    Image oldImage = imageService.getImageByRouteId(route.getRoute_id());
                    if (oldImage != null) {
                        imageService.deleteRouteById(route.getRoute_id());
                    }

                    String originalFilename = file.getOriginalFilename();
                    String fileExtention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    long fileSize = file.getSize();

                    // 파일을 저장할 경로를 Path 객체로 생성
                    Path path = Paths.get(uploadPath + "/" + originalFilename);

                    // 파일을 저장하는 로직
                    Files.write(path, file.getBytes());

                    Image image = new Image();
                    image.setRoute_id(route.getRoute_id());// route_Id 을 주입하는 부분
                    image.setFile_name(originalFilename);
                    image.setFile_extention(fileExtention);
                    image.setFile_size(fileSize);
                    image.setFile_path(uploadPath + "/" + originalFilename);

                    // 이미지 정보 DB에 삽입
                    this.imageService.insertImageByRouteId(image);
                } else {
                    // 클라이언트가 새로운 파일을 제공하지 않은 경우 기존의 이미지는 그대로 유지됩니다.
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

    //==============================어드민==================================
    @GetMapping("/admin/routes")
    @ResponseBody
    public List<Route> getAllRoutesForAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserVo loggedInUser = (UserVo) authentication.getPrincipal();

        model.addAttribute("loggedInUser", loggedInUser);

        List<Route> routes = routeService.findAllRoutesForAdmin();
        model.addAttribute("routes", routes);

        return routes;
    }

    @DeleteMapping("/{userId}/{routeId}")
    public ResponseEntity<Void> deleteRouteAdmin(
            @AuthenticationPrincipal UserVo loggedInUser, @PathVariable("userId") int user_id, @PathVariable("routeId") int route_id) {
        if (loggedInUser != null) {
            try {
                this.routeService.deleteRoute(route_id, user_id);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}

