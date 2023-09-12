package com.example.demo.controller;

import com.example.demo.dto.UserVo;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("/createAdmin")
    public String createAdmin() {
        userService.createAdminUser();
        return "/"; // 관리자 계정이 성공적으로 생성된 후 보여줄 페이지입니다.
    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserVo> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/user-page")
    public String userPage() {
        return "index";
    }

    @GetMapping
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(UserVo userVo, RedirectAttributes redirectAttributes) {
        if (!userService.isEmailUnique(userVo.getUser_email())) {
            redirectAttributes.addFlashAttribute("message", "중복된 이메일입니다");
            return "redirect:/";
        }

        userService.joinUser(userVo);
        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다");

        return "redirect:/";
    }

    /* 로그인 에러메시지 */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "main";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout"; // 로그아웃 후 로그인 페이지로 이동하도록 설정
    }
}