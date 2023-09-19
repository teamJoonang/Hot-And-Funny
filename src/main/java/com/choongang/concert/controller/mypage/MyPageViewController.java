package com.choongang.concert.controller.mypage;


import com.choongang.concert.dto.mypage.MyPageDto;
import com.choongang.concert.dto.mypage.MyTicketDto;
import com.choongang.concert.dto.user.FindEmailRequest;
import com.choongang.concert.dto.user.UserResponse;
import com.choongang.concert.service.user.MyPageService;
import com.choongang.concert.service.user.RegisterMail;
import com.choongang.concert.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/user")
public class MyPageViewController {

    private final UserService userService;
    private final MyPageService myPageService;
    
//  회원정보 조회 및 수정 가능페이지
    @GetMapping("/myinfo")
    public String getMyInfo(HttpServletRequest req , Model model) throws ServletException {
        log.info("get >> /user/myinfo | getMyinfo() 실행됨.");

        // 세션의 로그인 아이디 속성 값을 가져와서 이를 dto 멤버에 넣어준다.
        HttpSession session = req.getSession(false);
        String loginId = (String)session.getAttribute("loginId");
        FindEmailRequest findEmailRequest = new FindEmailRequest();
        findEmailRequest.setLoginId(loginId);
        // 넣어준 dto를 이용하여 service의 db 조회 , 사용자 정보를 받아 model에 넣어준다.
        UserResponse user = userService.findByEmail(findEmailRequest);

        if(user == null){
            return "redirect:/";
        }

        log.info("user::{}" , user);
        model.addAttribute("user" , user);

        return "mypage/myinfo";
    }

    @GetMapping("/myqna")
    public String getMyQna(HttpSession session, Model model){

        log.info("get >> /user/myqna  | getMyQna() 실행됨.");
        Long id = (Long) session.getAttribute("id");

        log.info("session key : value = {} : {}","id", id);

        List<MyPageDto> list = myPageService.findMyQna(id);
        model.addAttribute("list", list);

        return "mypage/myquestion";
    }

    //    티켓 구매 조회 페이지
    @GetMapping("/myticket")
    public String getMyTicket(HttpSession session, Model model){

        log.info("get >> /user/myticket | getMyTicket() 실행됨.");
        Long id = (Long) session.getAttribute("id");
        List<MyTicketDto> list = myPageService.findMyTicketList(id);
        log.info("User Session Id = {}", id);
        model.addAttribute("list", list);
        return "mypage/myticket";
    }

//    회원탈퇴 양식 페이지
    @GetMapping("/out")
    public String getOut(){

        log.info("get >> /user/out | getOut() 실행됨.");
        return "mypage/user_out";
    }


}
