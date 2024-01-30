package com.sparta.springauth.controller;

import com.sparta.springauth.dto.LoginRequestDto;
import com.sparta.springauth.dto.SignupRequestDto;
import com.sparta.springauth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {

    // UserService의 signup 메소드에 전달하기 위해 주입(DI) 받아와야 함
    private final UserService userService;

    // alt + ins로 생성자 만들어서 생성자로 주입해줬음
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(SignupRequestDto requestDto) {
        userService.signup(requestDto); // 위에서 생성자로 주입 받았으면 userService signup 메소드에 받아온 request(requestDto) 전달

        return "redirect:/api/user/login-page"; // 문제 없이 수행되면 회원가입이 다 완료된거니 로그인 하라는 의미로 로그인 페이지를 반환
    }


}