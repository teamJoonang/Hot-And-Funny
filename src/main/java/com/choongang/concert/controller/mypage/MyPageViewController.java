package com.choongang.concert.controller.mypage;


import com.choongang.concert.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/user")
public class MyPageViewController {

    private final UserService userService;

//  회원정보 조회 및 수정 가능페이지
    @GetMapping("myinfo")
    public String getMyInfo(){

        log.info("get >> /user/myinfo | getMyinfo() 실행됨.");

        return "mypage/myinfo";
    }

//    사용자 문의사항 조회 페이지
    @GetMapping("/myqna")
    public String getMyQna(){

        log.info("get >> /user/myqna  | getMyQna() 실행됨.");
        return "mypage/myquestion";
    }

//    티켓 구매 조회 페이지
    @GetMapping("/myticket")
    public String getMyTicket(){

        log.info("get >> /user/myticket | getMyTicket() 실행됨.");
        return "mypage/myticket";
    }

//    회원탈퇴 양식 페이지
    @GetMapping("/out")
    public String getOut(){

        log.info("get >> /user/out | getOut() 실행됨.");
        return "mypage/user_out";
    }

}
