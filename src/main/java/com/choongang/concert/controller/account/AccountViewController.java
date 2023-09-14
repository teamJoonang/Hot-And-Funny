package com.choongang.concert.controller.account;

import com.choongang.concert.dto.user.ResetPwRequest;
import com.choongang.concert.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
    public String login(ServletRequest request) throws ServletException {

//        log.info("get >> /user/login | login() 실행됨.");
        // 세션이 만약 있고 세션 안에 loginId라는 속성도 갖고 있다면 index 페이지로 리다이렉트.
        HttpServletRequest httpReq = (HttpServletRequest) request;

        HttpSession session = httpReq.getSession(false);
        if(session != null && session.getAttribute("loginId") != null){
            return "redirect:/";
        }
        return "accounts/login";
    }

//    회원가입 페이지
    @GetMapping("/signup")
    public String signup(HttpServletRequest request) throws ServletException{

        log.info("get >> /user/signup | signup() 실행됨.");
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("loginId") != null){
            return "redirect:/";
        }

        return "accounts/signup";
    }


    //    아이디 / 비밀번호 찾기 페이지
    @GetMapping("/find")
    public String findUserAccount(HttpServletRequest request) throws ServletException{

        log.info("get >> /user/find | findUserAccount() 실행됨.");
        // 세션이 만약 있고 세션 안에 loginId라는 속성도 갖고 있다면 index 페이지로 리다이렉트.
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("loginId") != null){
            return "redirect:/";
        }
        return "accounts/finduser";
    }

    //    비밀번호 변경 페이지
    @GetMapping("/reset")
    public String passwordReset(@ModelAttribute("user") ResetPwRequest resetPwReq , HttpServletRequest req) throws ServletException{

        log.info("get >> /user/reset | passwordReset() 실행됨.");

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("id") == null || session.getAttribute("loginId") == null){
            return "redirect:/";
        }

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
    public String logout(HttpServletRequest request , HttpServletResponse response) throws ServletException{

        // 세션이 만약 있고 세션 안에 loginId라는 속성도 갖고 있다면 index 페이지로 리다이렉트.
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("loginId") != null){
            session.removeAttribute("loginId");
            session.setMaxInactiveInterval(0);
            session.invalidate();
        }

        if(request.getCookies() != null ){
            for (Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("JSESSIONID")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "redirect:/";
    }

}
