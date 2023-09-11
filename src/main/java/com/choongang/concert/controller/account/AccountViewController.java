package com.choongang.concert.controller.account;

import com.choongang.concert.dto.user.ResetPwRequest;
import com.choongang.concert.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/user")
public class AccountViewController {

    private final UserService userService;

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


    //    아이디 / 비밀번호 찾기 페이지
    @GetMapping("/find")
    public String findUserAccount(){

        log.info("get >> /user/find | findUserAccount() 실행됨.");

        return "accounts/finduser";
    }

    //    비밀번호 변경 페이지
    @GetMapping("/reset")
    public String passwordReset(@ModelAttribute("user") ResetPwRequest resetPwReq , HttpSession session){

        log.info("get >> /user/reset | passwordReset() 실행됨.");
        log.info("session id : " + session.getAttribute("id"));
        log.info("session loginId : " + session.getAttribute("loginId"));

        // 세션으로부터 loginId를 가져온다.
        String loginId = (String)session.getAttribute("loginId");
        // 가져온 loginId를 dto에 넣어준다.
        resetPwReq.setLoginId(loginId);
        log.info("resetPwReq::{}" , resetPwReq);

        // 값이 담긴 dto와 함께 html을 반환한다.
        return "accounts/pw_reset";
    }


    // 로그아웃 구현해야함.
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate();

        return "redirect:/";
    }

}
