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

    @PostMapping("/user/login")
    public String login(LoginRequestDto requestDto, HttpServletResponse res) { // 로그인 성공하면 메인 페이지로 이동시킬거니 String으로
                                                                               // 바로 위와 마찬가지로 ModelAttribute 방식으로 받을건데 생략
                                                                               // HttpServletResponse 받아오기
                                                                               // - JWT Token 만들어서 cookie 생성&담아서 Response 객체에 넣어줘야 하잖슴(servlet이 만들어준 HttpServletResponse 받아오기)
                                                                               // 기억 안나면 JWT 다루기, Cookie 세션 다루기 다시보기
        try {
            userService.login(requestDto, res); // 1. 로그인할 때 검증하라고 받아온 데이터(requestDto) 보내주고
                                                // 2. 검증 다끝나면 JWT Token을 cookie에 넣고 또 cookie 담으라고 우리가 받아온 Request 객체(res)도 보내주기
                                                // 혹시 예외사항(잘못된요청...)이 발생했을 경우를 대비하여 로그인 페이지에 에러 표시(try-catch로 감싸기) - 단축키 : 감싸고 싶은 부분 뒤(;)에 .try하고 enter하면 자동 감싸짐
        } catch (Exception e) {
//            throw new RuntimeException(e);   이렇게 하지 않고 우리는 redirect해주기 - 여러 가지 방법 있음
            return "redirect:/api/user/login-page?error"; // client랑 약속해놓은게 있는데 이렇게 오류가 났을 떄 url 뒤에 '?error' 이렇게 요청을 좀 보내주세요~ 라고 client에서 요청이 들어왔어요
        }                                                 // 지금 상황은 클라이언트 요구사항에 의해서 ?error를 추가로 붙여준 상황



        return "redirect:/"; // 메인으로 redirect
    }
}