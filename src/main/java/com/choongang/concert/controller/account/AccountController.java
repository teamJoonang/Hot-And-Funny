package com.choongang.concert.controller.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/user")
public class AccountController {

//    로그인 페이지
    @GetMapping("/login")
    public String login(){

        log.info("get >> /user/login | login() 실행됨.");
        return "accounts/login";
    }

//    회원가입 페이지
    @GetMapping("/signup")
    public String signup(){

        log.info("get >> /user/signup | signup() 실행됨.");
        return "accounts/signup";
    }

//    비밀번호 변경 페이지
    @GetMapping("/reset")
    public String passwordReset(){

        log.info("get >> /user/reset | passwordReset() 실행됨.");
        return "accounts/pw_reset";
    }

//    아이디 / 비밀번호 찾기 페이지
    @GetMapping("/find")
    public String findUserAccount(){

        log.info("get >> /user/find | findUserAccount() 실행됨.");
        return "accounts/finduser";
    }
}
